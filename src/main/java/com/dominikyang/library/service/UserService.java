package com.dominikyang.library.service;

import com.dominikyang.library.entity.BorrowInfo;
import com.dominikyang.library.vo.LoginVO;

import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/25
 * 注释：null
 **/
public interface UserService {
    String login(LoginVO loginVO) ;

    boolean logout();

    List<BorrowInfo> orderList(Integer userId) ;
}
