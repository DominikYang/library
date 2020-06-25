package com.dominikyang.library.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * role
 * @author 
 */
@Data
public class Role implements Serializable {
    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;

    private static final long serialVersionUID = 1L;
}