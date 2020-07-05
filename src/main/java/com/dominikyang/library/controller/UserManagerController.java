package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.entity.LogAdmin;
import com.dominikyang.library.entity.LogWarn;
import com.dominikyang.library.entity.User;
import com.dominikyang.library.entity.UserRole;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.result.OperatorCode;
import com.dominikyang.library.service.AdminService;
import com.dominikyang.library.service.LogService;
import com.dominikyang.library.service.UserService;
import com.dominikyang.library.utils.TokenDecodeUtils;
import com.dominikyang.library.vo.RoleVO;
import com.dominikyang.library.vo.StateVO;
import com.dominikyang.library.vo.UserVO;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * 创建人：肖易安
 * 创建时间：  2020/6/27
 * 注释：null
 **/
@RestController
@RequestMapping("/admin/user")
public class UserManagerController {
    private static final Logger log = LoggerFactory.getLogger(UserManagerController.class);

    private UserService userService;
    private LogService logService;
    private AdminService adminService;

    @Autowired
    public UserManagerController(UserService userService, LogService logService, AdminService adminService) {
        this.userService = userService;
        this.logService = logService;
        this.adminService = adminService;
    }

    @PostMapping("/add")
    public BaseResult<String> addUser(UserVO userVO, HttpServletRequest httpServletRequest) throws GlobalException {
        String userid;
        try {
            userid = TokenDecodeUtils.getUserId(httpServletRequest);
        } catch (GlobalException e) {
            return BaseResult.fail(e.getCodeMessage());
        }
        if (!adminService.isAdmin(Integer.parseInt(userid))) {
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }
        User user = new User();
        user.setUsername(userVO.getUsername());
        user.setPassword(BCrypt.hashpw(userVO.getPassword(), BCrypt.gensalt())); //未作加密处理
        user.setCode(userVO.getCode());
        user.setDepartment(userVO.getDepartment());
        user.setGrade(userVO.getGrade());
        user.setMajor(userVO.getMajor());
        user.setRealName(userVO.getRealName());
        boolean success = userService.add(user);
        if (success) {
            LogAdmin logAdmin = new LogAdmin();
            logAdmin.setDetails("添加用户:" + userVO.getRealName());
            logAdmin.setOperateUserId(Integer.parseInt(userid));
            logAdmin.setOperateCode(OperatorCode.ADD_USER.getCode());
            logAdmin.setOperateName(OperatorCode.ADD_USER.getName());
            logAdmin.setTime(new Date());
            logService.addLogAdmin(logAdmin);
            log.info("用户" + userid + " 添加用户:" + userVO.getRealName());
            return BaseResult.success("添加成功");
        } else {
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(CodeMessage.ADD_FAILE.getCode() + "");
            logWarn.setWarnName("添加用户错误");
            logWarn.setDetails(CodeMessage.ADD_FAILE.getMessage());
            logService.addLogWarn(logWarn);
            log.warn("添加用户失败");
            return BaseResult.fail(CodeMessage.ADD_FAILE);
        }
    }

    @PostMapping("/edit")
    public BaseResult<String> editUser(UserVO userVO, HttpServletRequest httpServletRequest) throws GlobalException {
        String userid;
        try {
            userid = TokenDecodeUtils.getUserId(httpServletRequest);
        } catch (GlobalException e) {
            return BaseResult.fail(e.getCodeMessage());
        }
        if (!adminService.isAdmin(Integer.parseInt(userid))) {
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }
        User user = new User();
        user.setUsername(userVO.getUsername());
        user.setPassword(userVO.getPassword()); //未作加密处理
        user.setCode(userVO.getCode());
        user.setDepartment(userVO.getDepartment());
        user.setGrade(userVO.getGrade());
        user.setMajor(userVO.getMajor());
        user.setRealName(userVO.getRealName());
        user.setId(userVO.getId());
        try {
            boolean success = userService.edit(user);
            if (success) {
                LogAdmin logAdmin = new LogAdmin();
                logAdmin.setDetails(" 修改用户:" + userVO.getRealName());
                logAdmin.setOperateUserId(Integer.parseInt(userid));
                logAdmin.setOperateCode(OperatorCode.EDIT_USER.getCode());
                logAdmin.setOperateName(OperatorCode.EDIT_USER.getName());
                logAdmin.setTime(new Date());
                logService.addLogAdmin(logAdmin);
                log.info("用户" + userid + " 修改用户:" + userVO.getRealName());
                return BaseResult.success("修改成功");
            } else {
                LogWarn logWarn = new LogWarn();
                logWarn.setTime(new Date());
                logWarn.setWarnCode(CodeMessage.UPDATE_FAILE.getCode() + "");
                logWarn.setWarnName("修改用户错误");
                logWarn.setDetails(CodeMessage.UPDATE_FAILE.getMessage());
                logService.addLogWarn(logWarn);
                log.warn("修改用户信息失败");
                return BaseResult.fail(CodeMessage.UPDATE_FAILE);
            }
        } catch (GlobalException e) {
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(e.getCodeMessage().getCode() + "");
            logWarn.setWarnName("修改用户错误");
            logWarn.setDetails(e.getCodeMessage().getMessage());
            logService.addLogWarn(logWarn);
            log.warn(e.getCodeMessage().getMessage());
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @PostMapping("/state")
    public BaseResult<String> changeState(StateVO stateVO, HttpServletRequest httpServletRequest) throws GlobalException {
        String userid;
        try {
            userid = TokenDecodeUtils.getUserId(httpServletRequest);
        } catch (GlobalException e) {
            return BaseResult.fail(e.getCodeMessage());
        }
        if (!adminService.isAdmin(Integer.parseInt(userid))) {
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }
        try {
            boolean success = userService.changeState(stateVO);
            if (success) {
                LogAdmin logAdmin = new LogAdmin();
                logAdmin.setDetails(" 修改用户状态:" + stateVO.getUserId());
                logAdmin.setOperateUserId(Integer.parseInt(userid));
                logAdmin.setOperateCode(OperatorCode.CHANGE_USER_STATE.getCode());
                logAdmin.setOperateName(OperatorCode.CHANGE_USER_STATE.getName());
                logAdmin.setTime(new Date());
                logService.addLogAdmin(logAdmin);
                log.info("用户" + userid + " 修改用户状态:" + stateVO.getUserId());
                return BaseResult.success("修改成功");
            } else {
                LogWarn logWarn = new LogWarn();
                logWarn.setTime(new Date());
                logWarn.setWarnCode(CodeMessage.CHANGE_STATE_ERROR.getCode() + "");
                logWarn.setWarnName("修改状态错误");
                logWarn.setDetails(CodeMessage.CHANGE_STATE_ERROR.getMessage());
                logService.addLogWarn(logWarn);
                log.warn("修改状态失败");
                return BaseResult.fail(CodeMessage.CHANGE_STATE_ERROR);
            }
        } catch (GlobalException e) {
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(e.getCodeMessage().getCode() + "");
            logWarn.setWarnName("修改状态错误");
            logWarn.setDetails(e.getCodeMessage().getMessage());
            logService.addLogWarn(logWarn);
            log.warn(e.getCodeMessage().getMessage());
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @PostMapping("/role/add")
    public BaseResult<String> addUserRole(RoleVO roleVO, HttpServletRequest httpServletRequest) throws GlobalException {
        String userid;
        try {
            userid = TokenDecodeUtils.getUserId(httpServletRequest);
        } catch (GlobalException e) {
            return BaseResult.fail(e.getCodeMessage());
        }
        if (!adminService.isAdmin(Integer.parseInt(userid))) {
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }
        boolean success = userService.addRole(roleVO);
        if (success) {
            LogAdmin logAdmin = new LogAdmin();
            logAdmin.setDetails(" 添加角色：用户-" + roleVO.getUserId() + " 角色-" + roleVO.getRoleId());
            logAdmin.setOperateUserId(Integer.parseInt(userid));
            logAdmin.setOperateCode(OperatorCode.ADD_ROLE.getCode());
            logAdmin.setOperateName(OperatorCode.ADD_ROLE.getName());
            logAdmin.setTime(new Date());
            logService.addLogAdmin(logAdmin);
            log.info("用户" + userid + " 添加角色：用户-" + roleVO.getUserId() + " 角色-" + roleVO.getRoleId());
            return BaseResult.success("添加成功");
        } else {
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(CodeMessage.ADD_ROLE_ERROR.getCode() + "");
            logWarn.setWarnName("添加角色错误");
            logWarn.setDetails(CodeMessage.ADD_ROLE_ERROR.getMessage());
            logService.addLogWarn(logWarn);
            log.warn("添加角色失败");
            return BaseResult.fail(CodeMessage.ADD_ROLE_ERROR);
        }
    }

    @PostMapping("/role/del")
    public BaseResult<String> delUserRole(Integer id, HttpServletRequest httpServletRequest) throws GlobalException {
        String userid;
        try {
            userid = TokenDecodeUtils.getUserId(httpServletRequest);
        } catch (GlobalException e) {
            return BaseResult.fail(e.getCodeMessage());
        }
        if (!adminService.isAdmin(Integer.parseInt(userid))) {
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }
        boolean success = userService.delRole(id);
        if (success) {
            LogAdmin logAdmin = new LogAdmin();
            logAdmin.setDetails("删除角色Id：" + id);
            logAdmin.setOperateUserId(Integer.parseInt(userid));
            logAdmin.setOperateCode(OperatorCode.DEL_ROLE.getCode());
            logAdmin.setOperateName(OperatorCode.DEL_ROLE.getName());
            logAdmin.setTime(new Date());
            logService.addLogAdmin(logAdmin);
            log.info("用户" + userid + " 删除角色" + id);
            return BaseResult.success("删除成功");
        } else {
            LogWarn logWarn = new LogWarn();
            logWarn.setTime(new Date());
            logWarn.setWarnCode(CodeMessage.DEL_ROLE_ERROR.getCode() + "");
            logWarn.setWarnName("删除角色错误");
            logWarn.setDetails(CodeMessage.DEL_ROLE_ERROR.getMessage());
            logService.addLogWarn(logWarn);
            log.warn("删除角色失败");
            return BaseResult.fail(CodeMessage.DEL_ROLE_ERROR);
        }
    }

    @PostMapping("/role/list")
    public BaseResult<List<UserRole>> listUserRole(Integer userId, HttpServletRequest httpServletRequest) throws GlobalException {
        String userid;
        try {
            userid = TokenDecodeUtils.getUserId(httpServletRequest);
        } catch (GlobalException e) {
            return BaseResult.fail(e.getCodeMessage());
        }
        if (!adminService.isAdmin(Integer.parseInt(userid))) {
            return BaseResult.fail(CodeMessage.NOT_MANAGER);
        }
        List<UserRole> userRoles = userService.listRole(userId);
        LogAdmin logAdmin = new LogAdmin();
        logAdmin.setDetails("查看角色列表");
        logAdmin.setOperateUserId(Integer.parseInt(userid));
        logAdmin.setOperateCode(OperatorCode.LIST_ROLE.getCode());
        logAdmin.setOperateName(OperatorCode.LIST_ROLE.getName());
        logAdmin.setTime(new Date());
        logService.addLogAdmin(logAdmin);
        log.info("用户" + userid + " 查看角色列表");
        if (userRoles.size() < 1) {
            return null;
        } else {
            return BaseResult.success(userRoles);
        }
    }
}
