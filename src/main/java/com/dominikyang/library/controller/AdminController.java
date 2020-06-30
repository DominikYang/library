package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.entity.Role;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.AdminService;
import com.dominikyang.library.utils.RedisUtils;
import com.dominikyang.library.vo.LoginVO;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/30
 * 注释：null
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {
    private static long tokenTimeOut = 86400 ;

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public BaseResult<String> login(LoginVO loginVO, HttpServletRequest httpServletRequest) {
        try {
            String login = adminService.login(loginVO);
            String userId = JWT.decode(login).getAudience().get(0);
            RedisUtils.set(userId,login,tokenTimeOut);
            return BaseResult.success(login);
        } catch (GlobalException e) {
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @GetMapping("/logout")
    public BaseResult<String> logout(HttpServletRequest httpServletRequest){
        String userId = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        boolean success = RedisUtils.del(userId);
        if(success){
            return BaseResult.success(null);
        }else{
            return BaseResult.fail(CodeMessage.LOGOUT_FAILE);
        }
    }

    @GetMapping("/role/list")
    public BaseResult<List<Role>> roleList(HttpServletRequest httpServletRequest){
        List<Role> roles = adminService.getRoles();
        return BaseResult.success(roles);
    }
}
