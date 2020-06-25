package com.dominikyang.library.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * role_power
 * @author 
 */
@Data
public class RolePower implements Serializable {
    /**
     * 关联表id
     */
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer powerId;

    private static final long serialVersionUID = 1L;
}