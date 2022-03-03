package com.springboot.springtestx2.IoC;

import com.springboot.springtestx2.IoC.config.AppConfig;
import com.springboot.springtestx2.IoC.pojo.BusinessPerson;
import com.springboot.springtestx2.IoC.pojo.defenition.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IoCTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//        User user = ctx.getBean(User.class);
        Person person = ctx.getBean(BusinessPerson.class);
        person.service();
    }
}