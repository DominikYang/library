package com.dominikyang.library.service;

import com.dominikyang.library.entity.Role;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.vo.LoginVO;

import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/30
 * 注释：null
 **/
public interface AdminService {
    String login(LoginVO loginVO) throws GlobalException;

    List<Role> getRoles();

    boolean isAdmin(Integer userId) throws GlobalException;
}
