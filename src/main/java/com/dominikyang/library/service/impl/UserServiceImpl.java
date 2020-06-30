package com.dominikyang.library.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dominikyang.library.dao.BorrowInfoDao;
import com.dominikyang.library.dao.UserDao;
import com.dominikyang.library.dao.UserRoleDao;
import com.dominikyang.library.entity.*;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.UserService;
import com.dominikyang.library.vo.LoginVO;
import com.dominikyang.library.vo.RoleVO;
import com.dominikyang.library.vo.StateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/25
 * 注释：null
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public String login(LoginVO loginVO) throws GlobalException {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(loginVO.getUsername());
        List<User> users = userDao.selectByExample(example);
        if (users.size() < 1) {
            throw new GlobalException(CodeMessage.ERROR_USERNAME);
        } else if (users.size() > 1) {
            throw new GlobalException(CodeMessage.DATABSE_ERROR);
        } else {
            if (users.get(0).getPassword().equals(loginVO.getPassword())) {
                return getToken(users.get(0));
            } else {
                throw new GlobalException(CodeMessage.ERROR_PASSWORD);
            }
        }
    }

    @Override
    public boolean add(User user) {
        try{
            userDao.insert(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean edit(User user) throws GlobalException {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword())
                .andCodeEqualTo(user.getCode()).andRealNameEqualTo(user.getRealName()).andGradeEqualTo(user.getGrade())
                .andDepartmentEqualTo(user.getDepartment()).andMajorEqualTo(user.getMajor());
        UserExample old = new UserExample();
        old.createCriteria().andIdEqualTo(user.getId());
        List<User> users = userDao.selectByExample(old);
        if (users.size() > 1) {
            throw new GlobalException(CodeMessage.DATABSE_ERROR);
        } else if (users.size() < 1) {
            throw new GlobalException(CodeMessage.HAVE_NOT_USER);
        } else {
            try{
                userDao.updateByExample(users.get(0), example);
                return true;
            }catch (Exception e){
                throw new GlobalException(CodeMessage.UPDATE_FAILE);
            }
        }
    }

    @Override
    public boolean changeState(StateVO stateVO) throws GlobalException {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(stateVO.getUserId());
        List<User> users = userDao.selectByExample(example);
        if (users.size() < 1) {
            throw new GlobalException(CodeMessage.HAVE_NOT_USER);
        } else if (users.size() > 1) {
            throw new GlobalException(CodeMessage.DATABSE_ERROR);
        } else {
            example.clear();
            example.createCriteria().andStateEqualTo(stateVO.getState());
            try{
                userDao.updateByExample(users.get(0), example);
                return true;
            }catch (Exception e){
                throw new GlobalException(CodeMessage.UPDATE_FAILE);
            }
        }
    }

    @Override
    public boolean addRole(RoleVO roleVO) {
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleVO.getRoleId());
        userRole.setUserId(roleVO.getUserId());
        try{
            userRoleDao.insert(userRole);
            return true ;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delRole(Integer id) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andIdEqualTo(id);
        try{
            userRoleDao.deleteByExample(example);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<UserRole> listRole(Integer userId) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserRole> userRoles = userRoleDao.selectByExample(example);
        return userRoles;
    }

    @Override
    public User getUser(Integer userId) throws GlobalException {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(userId);
        List<User> users = userDao.selectByExample(example);
        if (users.size() < 1) {
            throw new GlobalException(CodeMessage.ERROR_USERNAME);
        } else if (users.size() > 1) {
            throw new GlobalException(CodeMessage.DATABSE_ERROR);
        } else {
            return users.get(0);
        }
    }

    public String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(String.valueOf(user.getId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

}
