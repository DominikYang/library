package com.dominikyang.library.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author Wei yuyaung
 * @date 2020.07.03 9:27
 */
public class ExceptionUtils {

    public String helloException(String hello, BlockException ex){
        System.out.println("Oops: " + ex.getClass().getCanonicalName());
        return "fail in helloService: " + hello;
    }
}
