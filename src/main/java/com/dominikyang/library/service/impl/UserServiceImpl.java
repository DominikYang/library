package com.dominikyang.library.service.impl;

import com.dominikyang.library.dao.UserDao;
import com.dominikyang.library.entity.BorrowInfo;
import com.dominikyang.library.entity.User;
import com.dominikyang.library.entity.UserExample;
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
    public String login(LoginVO loginVO) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(loginVO.getUsername());
        List<User> users = userDao.selectByExample(example);
        if(users.size()<1){

        }else if(users.size()>1){

        }else{
            if(users.get(0).getPassword().equals(loginVO.getPassword())){
                return "success" ;
            }else{

            }
        }
        return null;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public List<BorrowInfo> orderList(Integer userId) {
        return null;
    }
}
