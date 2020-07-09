package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.commons.CommonFinalValues;
import com.dominikyang.library.entity.LogAdmin;
import com.dominikyang.library.entity.LogWarn;
import com.dominikyang.library.entity.Role;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.result.OperatorCode;
import com.dominikyang.library.service.AdminService;
import com.dominikyang.library.service.LogService;
import com.dominikyang.library.utils.RedisUtils;
import com.dominikyang.library.utils.TokenDecodeUtils;
import com.dominikyang.library.vo.LoginVO;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);


    private final AdminService adminService;
    private final LogService logService;
    private RedisTemplate<String,Object> redisTemplate;

    public AdminController(AdminService adminService, LogService logService, RedisTemplate<String, Object> redisTemplate) {
        this.adminService = adminService;
        this.logService = logService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/login")
    public BaseResult<String> login(LoginVO loginVO, HttpServletRequest httpServletRequest) throws GlobalException {
        try {
            String login = adminService.login(loginVO);
            String userId = JWT.decode(login).getAudience().get(0);
            redisTemplate.opsForValue().set(userId, login, CommonFinalValues.TOKEN_TIME_OUT);
            return BaseResult.success(login);
        } catch (GlobalException e) {
            LogWarn logWarn = new LogWarn(
                    e.getCodeMessage().getCode() + "",
                    "异常登录请求",
                    e.getCodeMessage().getMessage(),
                    new Date());
            logService.addLogWarn(logWarn);
            log.warn(e.getCodeMessage().getMessage());
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @GetMapping("/logout")
    public BaseResult<String> logout(HttpServletRequest httpServletRequest) throws GlobalException {
        String userId;
        try {
            userId = TokenDecodeUtils.getUserId(httpServletRequest);
        } catch (GlobalException e) {
            return BaseResult.fail(e.getCodeMessage());
        }
        boolean success = RedisUtils.del(userId);
        if (success) {
            return BaseResult.success(null);
        } else {
            LogWarn logWarn = new LogWarn(
                    CodeMessage.LOGOUT_FAILE.getCode() + "",
                    "注销删除token失败",
                    CodeMessage.LOGOUT_FAILE.getMessage(),
                    new Date());
            logService.addLogWarn(logWarn);
            log.warn(CodeMessage.LOGOUT_FAILE.getMessage());
            return BaseResult.fail(CodeMessage.LOGOUT_FAILE);
        }
    }

    @GetMapping("/role/list")
    public BaseResult<List<Role>> roleList(HttpServletRequest httpServletRequest) throws GlobalException {
        String userId = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        if (adminService.isAdmin(Integer.parseInt(userId))) {
            List<Role> roles = adminService.getRoles();
            LogAdmin logAdmin = new LogAdmin();
            logAdmin.setDetails("查看角色表");
            logAdmin.setOperateUserId(Integer.parseInt(userId));
            logAdmin.setOperateCode(OperatorCode.GET_ROLES.getCode());
            logAdmin.setOperateName(OperatorCode.GET_ROLES.getName());
            logAdmin.setTime(new Date());
            logService.addLogAdmin(logAdmin);
            log.info("用户" + userId + " 查看角色表");
            return BaseResult.success(roles);
        } else {
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }

    }
}
