package com.springboot.springtestx2.IoC.config;

import org.springframework.context.annotation.ComponentScan;

/* @Configuration -- https://zhuanlan.zhihu.com/p/123194781*/
//@Configuration
@ComponentScan(basePackages = "com.springboot.springtestx2.IoC.*",
        excludeFilters = {@ComponentScan.Filter()})
public class AppConfig {
/*    @Bean
    public User initUser(){
        return new User(1L,"user_name_1","note_1");
    }*/
}
