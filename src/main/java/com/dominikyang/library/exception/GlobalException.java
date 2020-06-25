package com.dominikyang.library.exception;

import com.dominikyang.library.result.CodeMessage;
import lombok.Getter;

/**
 * 创建人：肖易安
 * 创建时间：  2020/6/25
 * 注释：null
 **/
@Getter
public class GlobalException extends Exception {

    private static final long serialVersionUID = -3655264493408473309L;

    private CodeMessage codeMessage;

    public GlobalException(CodeMessage codeMessage) {
        super(codeMessage.getMessage());
        this.codeMessage = codeMessage;
    }

}
