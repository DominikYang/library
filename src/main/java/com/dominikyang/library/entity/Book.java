package com.dominikyang.library.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * book
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    /**
     * 图书id，主键
     */
    private Integer id;

    /**
     * 书名
     */
    private String name;

    /**
     * 出版时间
     */
    private Date publishDate;

    /**
     * ISBN编号
     */
    private String isbn;

    /**
     * 作者
     */
    private String author;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 内容简介
     */
    private String introduction;

    /**
     * 库存数量
     */
    private Integer number;

    /**
     * 状态
     */
    private Integer state;

    private static final long serialVersionUID = 1L;

    public Book(Integer id) {
        this.id = id;
    }

    public Book(Integer id, Integer state) {
        this.id = id;
        this.state = state;
    }
}