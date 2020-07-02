package com.dominikyang.library.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dominikyang.library.dao.RoleDao;
import com.dominikyang.library.dao.UserDao;
import com.dominikyang.library.dao.UserRoleDao;
import com.dominikyang.library.entity.*;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.AdminService;
import com.dominikyang.library.vo.LoginVO;
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
    @Autowired
    private UserDao userDao ;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public String login(LoginVO loginVO) throws GlobalException {
        boolean isAdmin = false;
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(loginVO.getUsername());
        List<User> users = userDao.selectByExample(example);
        if (users.size() < 1) {
            throw new GlobalException(CodeMessage.ERROR_USERNAME);
        } else if (users.size() > 1) {
            throw new GlobalException(CodeMessage.DATABSE_ERROR);
        } else if (users.get(0).getPassword().equals(loginVO.getPassword())) {
            UserRoleExample example1 = new UserRoleExample();
            example1.createCriteria().andUserIdEqualTo(users.get(0).getId());
            List<UserRole> roles = userRoleDao.selectByExample(example1);
            for (UserRole role:roles) {
                if (role.getRoleId() == 5) {
                    isAdmin = true;
                    break;
                }
            }
        } else {
            throw new GlobalException(CodeMessage.ERROR_PASSWORD);
        }
        if(isAdmin){
            return getToken(users.get(0));
        }else{
            throw new GlobalException(CodeMessage.NOT_MANAGER);
        }
    }

    @Override
    public List<Role> getRoles() {
        RoleExample example = new RoleExample();
        example.createCriteria().andIdIsNotNull();
        List<Role> roles = roleDao.selectByExample(example);
        return roles;
    }

    public String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(String.valueOf(user.getId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
