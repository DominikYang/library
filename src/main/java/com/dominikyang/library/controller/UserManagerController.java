package com.dominikyang.library.controller;

import com.auth0.jwt.JWT;
import com.dominikyang.library.entity.User;
import com.dominikyang.library.entity.UserRole;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.UserService;
import com.dominikyang.library.vo.RoleVO;
import com.dominikyang.library.vo.StateVO;
import com.dominikyang.library.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    private UserService userService ;

    @Autowired
    public UserManagerController(UserService userService){
        this.userService = userService ;
    }

    @PostMapping("/add")
    public BaseResult<String> addUser(UserVO userVO, HttpServletRequest httpServletRequest){
        String userid = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        User user = new User();
        user.setUsername(userVO.getUsername());
        user.setPassword(userVO.getPassword()); //未作加密处理
        user.setCode(userVO.getCode());
        user.setDepartment(userVO.getDepartment());
        user.setGrade(userVO.getGrade());
        user.setMajor(userVO.getMajor());
        user.setRealName(userVO.getRealName());
        boolean success = userService.add(user);
        if(success){
            log.info("用户"+userid+" 添加用户:"+userVO.getRealName());
            return BaseResult.success("添加成功");
        }else{
            log.warn("添加用户失败");
            return BaseResult.fail(CodeMessage.ADD_FAILE);
        }
    }

    @PostMapping("/edit")
    public BaseResult<String> editUser(UserVO userVO,HttpServletRequest httpServletRequest){
        String userid = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        User user = new User();
        user.setUsername(userVO.getUsername());
        user.setPassword(userVO.getPassword()); //未作加密处理
        user.setCode(userVO.getCode());
        user.setDepartment(userVO.getDepartment());
        user.setGrade(userVO.getGrade());
        user.setMajor(userVO.getMajor());
        user.setRealName(userVO.getRealName());
        user.setId(userVO.getId());
        try{
            boolean success = userService.edit(user);
            if(success){
                log.info("用户"+userid+" 修改用户:"+userVO.getRealName());
                return BaseResult.success("修改成功");
            }else{
                log.warn("修改用户信息失败");
                return BaseResult.fail(CodeMessage.UPDATE_FAILE);
            }
        } catch (GlobalException e) {
            log.warn(e.getCodeMessage().getMessage());
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @PostMapping("/state")
    public BaseResult<String> changeState(StateVO stateVO,HttpServletRequest httpServletRequest){
        String userid = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        try{
            boolean success = userService.changeState(stateVO);
            if(success){
                log.info("用户"+userid+" 修改用户状态:"+stateVO.getUserId());
                return BaseResult.success("修改成功");
            }else{
                log.warn("修改状态失败");
                return BaseResult.fail(CodeMessage.CHANGE_STATE_ERROR);
            }
        } catch (GlobalException e) {
            log.warn(e.getCodeMessage().getMessage());
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @PostMapping("/role/add")
    public BaseResult<String> addUserRole(RoleVO roleVO,HttpServletRequest httpServletRequest){
        String userid = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        boolean success = userService.addRole(roleVO);
        if(success){
            log.info("用户"+userid+" 添加角色：用户-"+roleVO.getUserId()+" 角色-"+roleVO.getRoleId());
            return BaseResult.success("添加成功");
        }else{
            log.warn("添加角色失败");
            return BaseResult.fail(CodeMessage.ADD_ROLE_ERROR);
        }
    }

    @PostMapping("/role/del")
    public BaseResult<String> delUserRole(Integer id,HttpServletRequest httpServletRequest){
        String userid = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        boolean success = userService.delRole(id);
        if(success){
            log.info("用户"+userid+" 删除角色"+id);
            return BaseResult.success("删除成功");
        }else{
            log.warn("删除角色失败");
            return BaseResult.fail(CodeMessage.DEL_ROLE_ERROR);
        }
    }

    @PostMapping("/role/list")
    public BaseResult<List<UserRole>> listUserRole(Integer userId,HttpServletRequest httpServletRequest){
        String userid = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        List<UserRole> userRoles = userService.listRole(userId);
        log.info("用户"+userid+" 查看角色列表");
        if(userRoles.size()<1){
            return null;
        }else{
            return BaseResult.success(userRoles);
        }
    }
}
