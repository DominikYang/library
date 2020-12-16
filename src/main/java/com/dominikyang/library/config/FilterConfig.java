package com.dominikyang.library.config;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;


/**
 * @author Wei yuyaung
 * @date 2020.07.05 20:23
 */
@Configuration
public class FilterConfig {

/*    @Bean
    public FilterRegistrationBean sentinelFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/api/v1/*");
        registration.setName("sentinelFilter");
        registration.setOrder(1);

        return registration;
    }*/
}
