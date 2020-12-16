package com.dominikyang.library.advice;

import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.BaseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author wyy
 * @date 2020.09.27 18:04
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public BaseResult<String> globalExceptionHandler(GlobalException e) {
        return BaseResult.fail(e.getCodeMessage());
    }
}
