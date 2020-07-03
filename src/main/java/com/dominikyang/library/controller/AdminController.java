package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.entity.LogAdmin;
import com.dominikyang.library.entity.LogWarn;
import com.dominikyang.library.entity.Role;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.AdminService;
import com.dominikyang.library.service.LogService;
import com.dominikyang.library.utils.RedisUtils;
import com.dominikyang.library.utils.TokenDecodeUtils;
import com.dominikyang.library.vo.LoginVO;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/30
 * 注释：null
 **/
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private static long tokenTimeOut = 86400 ;

    private AdminService adminService;
    private LogService logService;

    @Autowired
    public AdminController(AdminService adminService,LogService logService){
        this.logService =logService;
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public BaseResult<String> login(LoginVO loginVO, HttpServletRequest httpServletRequest) throws GlobalException {
        try {
            String login = adminService.login(loginVO);
            String userId = JWT.decode(login).getAudience().get(0);
            RedisUtils.set(userId,login,tokenTimeOut);
            return BaseResult.success(login);
        } catch (GlobalException e) {
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(e.getCodeMessage().getCode()+"");
            logWarn.setWarnName("登陆错误");
            logWarn.setDetails(e.getCodeMessage().getMessage());
            logService.addLogWarn(logWarn);
            log.warn(e.getCodeMessage().getMessage());
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @GetMapping("/logout")
    public BaseResult<String> logout(HttpServletRequest httpServletRequest) throws GlobalException {
        String userId ;
        try{
            userId = TokenDecodeUtils.getUserId(httpServletRequest);
        }catch (GlobalException e){
            return BaseResult.fail(e.getCodeMessage());
        }
        boolean success = RedisUtils.del(userId);
        if(success){
            return BaseResult.success(null);
        }else{
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(CodeMessage.LOGOUT_FAILE.getCode()+"");
            logWarn.setWarnName("注销错误");
            logWarn.setDetails(CodeMessage.LOGOUT_FAILE.getMessage());
            logService.addLogWarn(logWarn);
            log.warn(CodeMessage.LOGOUT_FAILE.getMessage());
            return BaseResult.fail(CodeMessage.LOGOUT_FAILE);
        }
    }

    @GetMapping("/role/list")
    public BaseResult<List<Role>> roleList(HttpServletRequest httpServletRequest) throws GlobalException {
        String userid = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        if(adminService.isAdmin(Integer.parseInt(userid))){
            List<Role> roles = adminService.getRoles();
            LogAdmin logAdmin = new LogAdmin();
            logAdmin.setDetails("查看角色表");
            logAdmin.setOperateUserId(Integer.parseInt(userid));
            logAdmin.setOperateName("查看角色表");
            logAdmin.setTime(new Date());
            logService.addLogAdmin(logAdmin);
            log.info("用户"+userid+" 查看角色表");
            return BaseResult.success(roles);
        }else{
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }

    }
}
