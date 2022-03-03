package com.springboot.springtestx2.IoC.pojo;

import com.springboot.springtestx2.IoC.pojo.defenition.Animal;
import org.springframework.stereotype.Component;

@Component
public class Animal_Dog implements Animal {
    @Override
    public void use() {
        System.out.println("dog {"+ Animal_Dog.class.getSimpleName()+"}");
    }
}
