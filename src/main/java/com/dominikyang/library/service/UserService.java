package com.dominikyang.library.service;

import com.dominikyang.library.entity.BorrowInfo;
import com.dominikyang.library.entity.User;
import com.dominikyang.library.entity.UserRole;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.vo.LoginVO;
import com.dominikyang.library.vo.RoleVO;
import com.dominikyang.library.vo.StateVO;
import com.dominikyang.library.vo.UserVO;

import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/25
 * 注释：null
 **/
public interface UserService {
    String login(LoginVO loginVO) throws GlobalException;

    boolean add(User user);

    boolean edit(User user) throws GlobalException;

    boolean changeState(StateVO stateVO) throws GlobalException;

    boolean addRole(RoleVO roleVO);

    boolean delRole(Integer roleId);

    List<UserRole> listRole(Integer userId);
}
