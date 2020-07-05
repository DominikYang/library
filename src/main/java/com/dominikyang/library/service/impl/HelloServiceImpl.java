package com.dominikyang.library.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dominikyang.library.exception.ExceptionUtils;
import com.dominikyang.library.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author Wei yuyaung
 * @date 2020.07.03 10:49
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    @SentinelResource(value = "hello",blockHandler = "helloException",blockHandlerClass = {ExceptionUtils.class})
    public String hello(String hello) {
        return "hello " + hello;
    }

    public String defaultFallback() {
        System.out.println("Go to default fallback");
        return "default_fallback";
    }
}
