package com.dominikyang.library.service;

import com.dominikyang.library.entity.Book;
import com.dominikyang.library.entity.BorrowInfo;
import com.dominikyang.library.entity.User;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.vo.StateVO;

import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/27
 * 注释：null
 **/
public interface OrderService {
    List<BorrowInfo> orderList(Integer userId);

    String createOrder(Book book, User user) throws GlobalException;

    boolean returnOrder(Integer id, Integer state);
}
