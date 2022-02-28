package com.springboot.springtestx2;

import com.springboot.springtestx2.config.AppConfig;
import com.springboot.springtestx2.pojo.defenition.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IoCTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//        User user = ctx.getBean(User.class);
        Person person = ctx.getBean(Person.class);
        person.service();
    }
}