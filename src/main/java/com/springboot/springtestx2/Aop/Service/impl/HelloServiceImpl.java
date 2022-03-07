package com.springboot.springtestx2.Aop.Service.impl;

import com.springboot.springtestx2.Aop.Service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        System.out.println("Hello" + name);
        return "Hello" +name;
    }
}
