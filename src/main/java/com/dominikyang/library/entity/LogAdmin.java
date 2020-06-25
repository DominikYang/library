package com.dominikyang.library.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * log_admin
 * @author 
 */
@Data
public class LogAdmin implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 执行操作的用户id
     */
    private Integer operateUserId;

    /**
     * 操作代码
     */
    private String operateCode;

    /**
     * 操作名称
     */
    private String operateName;

    /**
     * 详细信息
     */
    private String details;

    /**
     * 时间
     */
    private Date time;

    private static final long serialVersionUID = 1L;
}