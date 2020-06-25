package com.dominikyang.library.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    /**
     * 用户id，主键
     */
    private Integer id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户编号，唯一索引
     */
    private Integer code;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 所属院系
     */
    private String department;

    /**
     * 年级
     */
    private Integer grade;

    /**
     * 专业
     */
    private String major;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 角色id
     */
    private Integer role;

    private static final long serialVersionUID = 1L;
}