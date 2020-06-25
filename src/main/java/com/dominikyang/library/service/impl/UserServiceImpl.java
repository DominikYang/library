package com.dominikyang.library.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dominikyang.library.dao.UserDao;
import com.dominikyang.library.entity.BorrowInfo;
import com.dominikyang.library.entity.User;
import com.dominikyang.library.entity.UserExample;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.UserService;
import com.dominikyang.library.vo.LoginVO;
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
    private UserDao userDao ;

    @Override
    public String login(LoginVO loginVO) throws GlobalException {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(loginVO.getUsername());
        List<User> users = userDao.selectByExample(example);
        if(users.size()<1){
            throw new GlobalException(CodeMessage.ERROR_USERNAME);
        }else if(users.size()>1){
            throw new GlobalException(CodeMessage.DATABSE_ERROR);
        }else{
            if(users.get(0).getPassword().equals(loginVO.getPassword())){
                return getToken(users.get(0)) ;
            }else{
                throw new GlobalException(CodeMessage.ERROR_PASSWORD);
            }
        }
    }

    @Override
    public List<BorrowInfo> orderList(Integer userId) {
        return null;
    }

    public String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(String.valueOf(user.getId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
