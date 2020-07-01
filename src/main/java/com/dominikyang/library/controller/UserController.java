package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.entity.BorrowInfo;
import com.dominikyang.library.entity.LogWarn;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.LogService;
import com.dominikyang.library.service.OrderService;
import com.dominikyang.library.service.UserService;
import com.dominikyang.library.utils.RedisUtils;
import com.dominikyang.library.vo.LoginVO;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author Wei yuyaung
 * @date 2020.06.23 4:01
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    //token存在时间
    private static long tokenTimeOut = 86400;

    private UserService userService;
    private OrderService orderService;
    private LogService logService;

    @Autowired
    public UserController(UserService userService, OrderService orderService, LogService logService) {
        this.userService = userService;
        this.orderService = orderService;
        this.logService = logService;
    }

    @PostMapping("add")
    public String add() {
        String username = "username";
        String password = "password";
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return username + " " + hashPassword;
    }

    @PostMapping("login")
    public BaseResult<String> login(LoginVO loginVO, HttpServletRequest httpServletRequest) throws GlobalException {
        try {
            String login = userService.login(loginVO);
            String userId = JWT.decode(login).getAudience().get(0);
            RedisUtils.set(userId, login, tokenTimeOut);
            return BaseResult.success(login);
        } catch (GlobalException e) {
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(e.getCodeMessage().getCode() + "");
            logWarn.setWarnName("登陆错误");
            logWarn.setDetails(e.getCodeMessage().getMessage());
            logService.addLogWarn(logWarn);
            log.warn(e.getCodeMessage().getMessage());
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @GetMapping("logout")
    public BaseResult<String> logout(HttpServletRequest httpServletRequest) throws GlobalException {
        String userId = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        boolean success = RedisUtils.del(userId);
        if (success) {
            return BaseResult.success(null);
        } else {
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(CodeMessage.LOGOUT_FAILE.getCode() + "");
            logWarn.setWarnName("注销错误");
            logWarn.setDetails(CodeMessage.LOGOUT_FAILE.getMessage());
            logService.addLogWarn(logWarn);
            log.warn(CodeMessage.LOGOUT_FAILE.getMessage());
            return BaseResult.fail(CodeMessage.LOGOUT_FAILE);
        }
    }

    @GetMapping("order/list")
    public BaseResult<List<BorrowInfo>> orderList(HttpServletRequest httpServletRequest) {
        Integer userId = Integer.valueOf(JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0));
        List<BorrowInfo> borrowInfos = orderService.orderList(userId);
        if (borrowInfos.size() < 1) {
            return BaseResult.success(null);
        } else {
            return BaseResult.success(borrowInfos);
        }
    }
}
