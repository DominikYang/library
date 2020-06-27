package com.dominikyang.library.vo;

import lombok.Data;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/27
 * 注释：null
 **/
@Data
public class UserVO {
    private Integer id ;
    private String username;
    private String password;
    private Integer code;
    private String department;
    private String realName;
    private Integer grade;
    private String major;
}
