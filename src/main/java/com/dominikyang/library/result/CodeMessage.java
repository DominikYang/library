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
    public static final CodeMessage USER_NOT_EXIST = new CodeMessage(1004,"用户不存在");
    public static final CodeMessage USER_LOCKED = new CodeMessage(1005,"账户已被锁定");
    public static final CodeMessage CHANGE_NICKNAME_FAIL = new CodeMessage(1006,"修改昵称失败");
    public static final CodeMessage EMPTY_USER_MAIL_LIST = new CodeMessage(1007,"当前用户没有设置邮箱");
    public static final CodeMessage CHANGE_PASSWORD_FAIL = new CodeMessage(1008,"修改密码失败");
    public static final CodeMessage INSERT_MAIL_ACCOUNT_ERROR = new CodeMessage(1009,"邮箱插入数据库失败");
    public static final CodeMessage User_DOES_EXIST = new CodeMessage(1010,"该用户名已被使用");
    public static final CodeMessage ADD_NEW_USER_FAIL = new CodeMessage(1011,"新建用户失败");

    public static final CodeMessage NO_MAILBOX = new CodeMessage(2001,"没有绑定任何邮箱");
    public static final CodeMessage POP_ERROR = new CodeMessage(2002,"POP3服务运行错误");
    public static final CodeMessage SEND_MAIL_ERROR = new CodeMessage(2003,"发送邮件失败");
    public static final CodeMessage GET_MAIL_ERROR = new CodeMessage(2004,"获取邮件失败");
    public static final CodeMessage JUNK_MAIL = new CodeMessage(2005,"检测为垃圾邮件，发送失败") ;

    public static final CodeMessage NOT_MANAGER = new CodeMessage(3001,"没有管理员权限");
    public static final CodeMessage CHANGE_STATE_ERROR = new CodeMessage(3002,"修改状态失败") ;
    public static final CodeMessage DEL_USER_FAILE = new CodeMessage(3003,"删除用户失败");


    private int code;
    private String message;
}
