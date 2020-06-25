package com.dominikyang.library.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * category
 * @author 
 */
@Data
public class Category implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String category;

    private static final long serialVersionUID = 1L;
}