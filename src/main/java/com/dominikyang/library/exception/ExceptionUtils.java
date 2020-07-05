package com.dominikyang.library.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author Wei yuyaung
 * @date 2020.07.03 9:27
 */
public class ExceptionUtils {

    public static String helloException(String hello, BlockException ex) {
        System.out.println("Oops: " + ex.getClass().getCanonicalName());
        return "fail in helloService: " + hello;
    }

    public static String adminException(String info, BlockException ex) {
        return "fail in adminService: " + info + " limit";
    }

    public static String bookException(String info, BlockException ex) {
        return "fail in bookService: " + info + " limit";
    }

    public static String logException(String info, BlockException ex) {
        return "fail in logService: " + info + " limit";
    }
}
