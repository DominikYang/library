package com.dominikyang.library.service.impl;

import com.dominikyang.library.dao.BorrowInfoDao;
import com.dominikyang.library.entity.*;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.service.BookService;
import com.dominikyang.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/27
 * 注释：null
 **/
public class OrderServiceImpl implements OrderService {
    private static Integer borrowState = 0;
    private static Integer returnState = 1;
    private static long borrowTime = 60*1000*60*24*30 ;

    @Autowired
    private BorrowInfoDao borrowInfoDao;

    @Override
    public List<BorrowInfo> orderList(Integer userId) {
        BorrowInfoExample example = new BorrowInfoExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<BorrowInfo> borrowInfos = borrowInfoDao.selectByExample(example);
        if (borrowInfos.size() < 1) {
            return null;
        } else {
            return borrowInfos;
        }
    }

    @Override
    public String createOrder(Book book, User user) throws GlobalException {
        BorrowInfo info = new BorrowInfo();
        info.setBookId(book.getId());
        info.setBookIsbn(book.getIsbn());
        info.setBookName(book.getName());
        Date date = new Date();
        info.setBorrowTime(date);
        info.setEstimateReturnTime(new Date(date.getTime()+borrowTime));
        info.setUserId(user.getId());
        info.setUserCode(user.getCode());
        info.setUserName(user.getUsername());
        info.setOrderId("00001");  //订单号生成方式待定，暂时作为测试数据
        info.setState(borrowState);

        int success= borrowInfoDao.insert(info);
        if(success<1){
            throw new GlobalException(CodeMessage.CREATE_ORDER_ERROR);
        }else{
            return info.getOrderId();
        }
    }

    @Override
    public boolean returnOrder(Integer id, Integer state) {
        BorrowInfoExample example = new BorrowInfoExample();
        example.createCriteria().andIdEqualTo(id);
        BorrowInfo borrowInfo = new BorrowInfo();
        borrowInfo.setState(state);
        int success = borrowInfoDao.updateByExampleSelective(borrowInfo,example);
        if(success<1){
            return false;
        }else{
            return true;
        }
    }
}
