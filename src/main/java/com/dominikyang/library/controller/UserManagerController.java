package com.dominikyang.library.controller;

import com.dominikyang.library.entity.User;
import com.dominikyang.library.entity.UserRole;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.UserService;
import com.dominikyang.library.vo.RoleVO;
import com.dominikyang.library.vo.StateVO;
import com.dominikyang.library.vo.UserVO;
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
    private UserService userService ;

    @Autowired
    public UserManagerController(UserService userService){
        this.userService = userService ;
    }

    @PostMapping("/add")
    public BaseResult<String> addUser(UserVO userVO, HttpServletRequest httpServletRequest){
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
            return BaseResult.success("添加成功");
        }else{
            return BaseResult.fail(CodeMessage.ADD_FAILE);
        }
    }

    @PostMapping("/edit")
    public BaseResult<String> editUser(UserVO userVO,HttpServletRequest httpServletRequest){
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
                return BaseResult.success("修改成功");
            }else{
                return BaseResult.fail(CodeMessage.UPDATE_FAILE);
            }
        } catch (GlobalException e) {
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @PostMapping("/state")
    public BaseResult<String> changeState(StateVO stateVO,HttpServletRequest httpServletRequest){
        try{
            boolean success = userService.changeState(stateVO);
            if(success){
                return BaseResult.success("修改成功");
            }else{
                return BaseResult.fail(CodeMessage.CHANGE_STATE_ERROR);
            }
        } catch (GlobalException e) {
            return BaseResult.fail(e.getCodeMessage());
        }
    }

    @PostMapping("/role/add")
    public BaseResult<String> addUserRole(RoleVO roleVO,HttpServletRequest httpServletRequest){
        boolean success = userService.addRole(roleVO);
        if(success){
            return BaseResult.success("添加成功");
        }else{
            return BaseResult.fail(CodeMessage.ADD_ROLE_ERROR);
        }
    }

    @PostMapping("/role/del")
    public BaseResult<String> delUserRole(Integer id,HttpServletRequest httpServletRequest){
        boolean success = userService.delRole(id);
        if(success){
            return BaseResult.success("删除成功");
        }else{
            return BaseResult.fail(CodeMessage.DEL_ROLE_ERROR);
        }
    }

    @PostMapping("/role/list")
    public BaseResult<List<UserRole>> listUserRole(Integer userId,HttpServletRequest httpServletRequest){
        List<UserRole> userRoles = userService.listRole(userId);
        if(userRoles.size()<1){
            return null;
        }else{
            return BaseResult.success(userRoles);
        }
    }
}
