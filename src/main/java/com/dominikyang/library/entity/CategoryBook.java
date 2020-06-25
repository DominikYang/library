package com.dominikyang.library.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * category_book
 * @author 
 */
@Data
public class CategoryBook implements Serializable {
    /**
     * id,主键
     */
    private Integer id;

    /**
     * 图书id
     */
    private Integer bookId;

    /**
     * 分类id
     */
    private Integer categoryId;

    private static final long serialVersionUID = 1L;
}