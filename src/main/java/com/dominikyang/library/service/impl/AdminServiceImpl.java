package com.dominikyang.library.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dominikyang.library.commons.CommonFinalValues;
import com.dominikyang.library.dao.RoleDao;
import com.dominikyang.library.dao.UserDao;
import com.dominikyang.library.dao.UserRoleDao;
import com.dominikyang.library.entity.*;
import com.dominikyang.library.exception.ExceptionUtils;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.AdminService;
import com.dominikyang.library.service.UserService;
import com.dominikyang.library.vo.LoginVO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/30
 * 注释：null
 **/
@Service
public class AdminServiceImpl implements AdminService {

    private final UserDao userDao ;
    private final UserRoleDao userRoleDao;
    private final RoleDao roleDao;
    private final UserService userService;

    @SuppressWarnings("all")
    @Autowired
    public AdminServiceImpl(UserDao userDao, UserRoleDao userRoleDao, RoleDao roleDao, UserService userService) {
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
        this.roleDao = roleDao;
        this.userService = userService;
    }

    @Override
    public String login(LoginVO loginVO) throws GlobalException {
        String token;
        try{
             token = userService.login(loginVO, CommonFinalValues.ADMIN_ROLE_ID);
        }catch (GlobalException e){
            if(e.getCodeMessage().getCode() == CodeMessage.ROLE_INCORRECT.getCode()){
                throw new GlobalException(CodeMessage.NOT_MANAGER);
            }else {
                throw new GlobalException(e.getCodeMessage());
            }
        }
        return token;
    }

    @Override
    public List<Role> getRoles() {
        RoleExample example = new RoleExample();
        example.createCriteria().andIdIsNotNull();
        return roleDao.selectByExample(example);
    }

    @Override
    public boolean isAdmin(Integer userId) throws GlobalException {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserRole> userRoles = userRoleDao.selectByExample(example);
        if(userRoles.size() < 1) {
            throw new GlobalException(CodeMessage.NOT_MANAGER);
        }else {
            return userRoles.get(0).getRoleId() == CommonFinalValues.ADMIN_ROLE_ID;
        }
    }
}
