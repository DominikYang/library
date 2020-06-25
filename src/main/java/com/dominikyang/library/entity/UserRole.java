package com.dominikyang.library.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * user_role
 * @author 
 */
@Data
public class UserRole implements Serializable {
    /**
     * 关联表id
     */
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 用户id
     */
    private Integer userId;

    private static final long serialVersionUID = 1L;
}