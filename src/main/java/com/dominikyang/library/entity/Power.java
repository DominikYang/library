package com.dominikyang.library.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * power
 * @author 
 */
@Data
public class Power implements Serializable {
    /**
     * 权限id
     */
    private Integer id;

    /**
     * 权限名
     */
    private String powerName;

    /**
     * 权限内容说明
     */
    private String powerExplain;

    private static final long serialVersionUID = 1L;
}