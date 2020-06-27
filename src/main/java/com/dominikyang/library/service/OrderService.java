package com.dominikyang.library.service;

import com.dominikyang.library.entity.BorrowInfo;

import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/27
 * 注释：null
 **/
public interface OrderService {
    List<BorrowInfo> orderList(Integer userId) ;
}
