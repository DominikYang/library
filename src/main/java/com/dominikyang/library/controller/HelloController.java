package com.dominikyang.library.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.dominikyang.library.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author Wei yuyaung
 * @date 2020.06.23 2:34
 */
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/hello")
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HelloService helloService;
    /*
     * description: 测试 <br>
     * version: 1.0 <br>
     * date: 2020.06.23 2:36 <br>
     * author: Dominikyang <br>
     * 
     * @param name
     * @return java.lang.String
     */

    @GetMapping("/{name}")
    public String hello(@PathVariable String name){
        log.info("hello method called by: {}",name);
        redisTemplate.opsForValue().set("hello","hello " + name);
        return helloService.hello(name);
    }

/*    public String helloExceptionHandler(String name, BlockException e){
        log.info("触发hello方法限流");
        return "hello方法接口流量已达上限，访问被拒绝";
    }*/

    /**
     * description: 测试redis工作情况 <br>
     * version: 1.0 <br>
     * date: 2020.06.23 3:33 <br>
     * author: Dominikyang <br>
     * 
     * @param 
     * @return java.lang.String
     */ 
    @GetMapping("/redis")
    public String hello(){
        return Objects.requireNonNull(redisTemplate.opsForValue().get("hello")).toString();
    }
}
