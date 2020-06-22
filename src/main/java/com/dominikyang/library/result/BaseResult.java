package com.dominikyang.library.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wei yuyaung
 * @date 2020.05.19 12:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> {
    private Integer code;
    private String message;
    private T data;

    private BaseResult(T data){
        this.code = CodeMessage.CODE_SUCCESS;
        this.data = data;
    }

    private BaseResult(CodeMessage codeMessage){
        this.code = codeMessage.getCode();
        this.message = codeMessage.getMessage();
    }

    /**
     * description: 成功时候的返回 <br>
     * version: 1.0 <br>
     * date: 2020.06.23 3:25 <br>
     * author: Dominikyang <br>
     * 
     * @param data
     * @return com.dominikyang.library.result.BaseResult<T>
     */ 
    public static <T> BaseResult<T> success(T data){
        return new BaseResult<>(data);
    }

    /*
     * description: 失败时候的返回 <br>
     * version: 1.0 <br>
     * date: 2020.06.23 3:28 <br>
     * author: Dominikyang <br>
     * 
     * @param codeMessage
     * @return com.dominikyang.library.result.BaseResult<T>
     */ 
    public static <T> BaseResult<T> fail(CodeMessage codeMessage){
        return new BaseResult<>(codeMessage);
    }
}
