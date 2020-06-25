package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.entity.BorrowInfo;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.UserService;
import com.dominikyang.library.utils.RedisUtils;
import com.dominikyang.library.vo.LoginVO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Wei yuyaung
 * @date 2020.06.23 4:01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService ;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("add")
    public String add(){
        String username = "username";
        String password = "password";
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return username + " " + hashPassword;
    }

    @PostMapping("login")
    public BaseResult<String> login(LoginVO loginVO, HttpServletRequest httpServletRequest) {
        try {
            String login = userService.login(loginVO);
            String userId = JWT.decode(login).getAudience().get(0);
            RedisUtils.set(userId,login,86400);
            return BaseResult.success(login);
        } catch (GlobalException e) {
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @GetMapping("logout")
    public BaseResult<String> logout(HttpServletRequest httpServletRequest){
        String userId = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        boolean success = RedisUtils.del(userId);
        if(success){
            return BaseResult.success(null);
        }else{
            return BaseResult.fail(CodeMessage.LOGOUT_FAILE);
        }
    }

    @GetMapping("order/list")
    public BaseResult<List<BorrowInfo>> orderList(HttpServletRequest httpServletRequest){
        Integer userId = Integer.valueOf(JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0));
        List<BorrowInfo> borrowInfos = userService.orderList(userId);
        if(borrowInfos.size()<1){
            return BaseResult.success(null);
        }else{
            return BaseResult.success(borrowInfos);
        }
    }
}
