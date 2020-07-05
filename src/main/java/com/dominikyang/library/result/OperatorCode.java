package com.dominikyang.library.result;

import javafx.scene.input.KeyCodeCombination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建人：肖易安
 * 创建时间：  2020/7/5
 * 注释：null
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperatorCode {
    public static final OperatorCode GET_ROLES = new OperatorCode("0001", "查看角色表");
    public static final OperatorCode ADD_BOOK = new OperatorCode("0011", "添加书籍");
    public static final OperatorCode DEL_BOOK = new OperatorCode("0012", "删除书籍");
    public static final OperatorCode EDIT_BOOK = new OperatorCode("0013","编辑书籍");
    public static final OperatorCode ADD_USER = new OperatorCode("0021","添加用户");
    public static final OperatorCode EDIT_USER = new OperatorCode("0022","编辑用户");
    public static final OperatorCode CHANGE_USER_STATE = new OperatorCode("0023","改变用户状态");
    public static final OperatorCode ADD_ROLE = new OperatorCode("0031","添加角色");
    public static final OperatorCode DEL_ROLE = new OperatorCode("0032","删除角色");
    public static final OperatorCode LIST_ROLE = new OperatorCode("0033","查看角色列表");

    private String code;
    private String name;
}
