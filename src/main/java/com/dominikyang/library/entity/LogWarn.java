package com.dominikyang.library.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * log_warn
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogWarn implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 异常代码
     */
    private String warnCode;

    /**
     * 异常名称
     */
    private String warnName;

    /**
     * 具体日志
     */
    private String details;

    /**
     * 时间
     */
    private Date time;

    private static final long serialVersionUID = 1L;

    public LogWarn(String warnCode, String warnName, String details, Date time) {
        this.warnCode = warnCode;
        this.warnName = warnName;
        this.details = details;
        this.time = time;
    }
}