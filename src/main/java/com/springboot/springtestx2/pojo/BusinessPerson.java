package com.springboot.springtestx2.pojo;

import com.springboot.springtestx2.pojo.defenition.Animal;
import com.springboot.springtestx2.pojo.defenition.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BusinessPerson implements Person {
    @Qualifier("animal_Cat")
    @Autowired
    private Animal animal;

    @Override
    public void service() {
        this.animal.use();
    }

    @Override
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
