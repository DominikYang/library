package com.dominikyang.library.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * borrow_info
 * @author 
 */
@Data
public class User implements Serializable {
    /**
     * id，主键
     */
    private Integer id;

    /**
     * 订单号
     */
    private Integer orderId;

    /**
     * 图书id
     */
    private Integer bookId;

    /**
     * 图书名称
     */
    private String bookName;

    /**
     * 图书isbn
     */
    private String bookIsbn;

    /**
     * 借阅者id
     */
    private Integer userId;

    /**
     * 借阅者工号
     */
    private Integer userCode;

    /**
     * 借阅者姓名
     */
    private String userName;

    /**
     * 借出时间
     */
    private Date borrowTime;

    /**
     * 预计归还时间
     */
    private Date estimateReturnTime;

    /**
     * 实际归还时间
     */
    private Date realReturnTime;

    /**
     * 状态
     */
    private Integer state;

    private static final long serialVersionUID = 1L;
}