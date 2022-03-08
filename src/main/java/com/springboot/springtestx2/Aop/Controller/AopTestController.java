package com.springboot.springtestx2.Aop.Controller;

import com.springboot.springtestx2.Aop.Service.NameValidator;
import com.springboot.springtestx2.Aop.Service.impl.HelloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Aop")
public class AopTestController {
    @Autowired
    public HelloServiceImpl helloService;

    @GetMapping("/Test")
    public String test(String name){
        NameValidator nameValidator = (NameValidator) helloService;
        if (nameValidator.validate(name)){
            return helloService.sayHello(name);
        }
        return "name is Null";
    }
}
