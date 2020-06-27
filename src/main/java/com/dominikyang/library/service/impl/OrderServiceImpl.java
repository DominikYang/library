package com.dominikyang.library.service.impl;

import com.dominikyang.library.dao.BorrowInfoDao;
import com.dominikyang.library.entity.BorrowInfo;
import com.dominikyang.library.entity.BorrowInfoExample;
import com.dominikyang.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/27
 * 注释：null
 **/
public class OrderServiceImpl implements OrderService {
    @Autowired
    private BorrowInfoDao borrowInfoDao;

    @Override
    public List<BorrowInfo> orderList(Integer userId) {
        BorrowInfoExample example = new BorrowInfoExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<BorrowInfo> borrowInfos = borrowInfoDao.selectByExample(example);
        if(borrowInfos.size()<1){
            return null ;
        }else{
            return borrowInfos ;
        }
    }
}
