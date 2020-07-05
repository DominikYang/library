package com.dominikyang.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PreDestroy;
import java.util.Set;

/**
 * @author Wei yuyaung
 * @date 2020.07.05 15:18
 */
@Configuration
public class SpringConfig {

    private final RedisTemplate<String,Object> redisTemplate;

    public SpringConfig(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PreDestroy
    public void onDestroy(){
        Set<String> keys = redisTemplate.keys("*");
        assert keys != null;
        // 迭代
        for (String key : keys) {
            // 循环删除
            redisTemplate.delete(key);
        }
    }
}
