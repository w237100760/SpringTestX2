package com.springboot.springtestx2.config;

import com.springboot.springtestx2.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/* @Configuration -- https://zhuanlan.zhihu.com/p/123194781*/
@Configuration
@ComponentScan(basePackages = "com.springboot.springtestx2.*",
        excludeFilters = {@ComponentScan.Filter(classes = {})})
public class AppConfig {
    @Bean
    public User initUser(){
        return new User(1L,"user_name_1","note_1");
    }
}
