package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.commons.CommonFinalValues;
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
import org.springframework.data.redis.core.RedisTemplate;
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

    private final UserService userService;
    private final OrderService orderService;
    private final LogService logService;
    private final RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public UserController(UserService userService, OrderService orderService, LogService logService, RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.orderService = orderService;
        this.logService = logService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("add")
    public String add() {
        String username = "username";
        String password = "password";
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return username + " " + hashPassword;
    }

    @PostMapping("login")
    public BaseResult<String> login(LoginVO loginVO) throws GlobalException {
        try {
            String login = userService.login(loginVO);
            String userId = JWT.decode(login).getAudience().get(0);
            redisTemplate.opsForValue().set(userId,login,CommonFinalValues.TOKEN_TIME_OUT);
            return BaseResult.success(login);
        } catch (GlobalException e) {
            LogWarn logWarn =
                    new LogWarn(
                            e.getCodeMessage().getCode() + "",
                            "登陆错误",
                            e.getCodeMessage().getMessage(),
                            new Date());
            logService.addLogWarn(logWarn);
            log.warn(e.getCodeMessage().getMessage());
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @GetMapping("logout")
    public BaseResult<String> logout(HttpServletRequest httpServletRequest) throws GlobalException {
        String userId = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        boolean success = redisTemplate.delete(userId);
        if (success) {
            return BaseResult.success(null);
        } else {
            LogWarn logWarn = new LogWarn(
                    CodeMessage.LOGOUT_FAILE.getCode() + "",
                    "注销错误",
                    CodeMessage.LOGOUT_FAILE.getMessage(),
                    new Date());
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
