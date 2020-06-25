package com.dominikyang.library.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wei yuyaung
 * @date 2020.05.19 12:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeMessage {
    /**
     * description: 成功时默认返回200
     * @author: Wei Yuyang
     * @time: 2020.01.29
     */
    public static final int CODE_SUCCESS = 200;
    /**
     * description: 字段校验错误默认返回1
     * @author: Wei Yuyang
     * @time: 2020.01.30
     */
    public static final int CODE_PARAM_ERROR = 1;

    public static final CodeMessage NO_TOKEN = new CodeMessage(500,"token不存在，请重新登录");
    public static final CodeMessage VERIFY_FAIL = new CodeMessage(501,"token校验失败");

    public static final CodeMessage DATABSE_ERROR = new CodeMessage(1001,"数据库的数据有误");
    public static final CodeMessage ERROR_PASSWORD = new CodeMessage(1002,"密码错误");
    public static final CodeMessage ERROR_USERNAME = new CodeMessage(1003,"用户名不存在");
    public static final CodeMessage LOGOUT_FAILE = new CodeMessage(1004,"注销失败");

    public static final CodeMessage NOT_MANAGER = new CodeMessage(3001,"没有管理员权限");
    public static final CodeMessage CHANGE_STATE_ERROR = new CodeMessage(3002,"修改状态失败") ;
    public static final CodeMessage DEL_USER_FAILE = new CodeMessage(3003,"删除用户失败");


    private int code;
    private String message;
}
