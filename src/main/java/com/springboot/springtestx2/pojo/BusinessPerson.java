package com.springboot.springtestx2.pojo;

import com.springboot.springtestx2.pojo.defenition.Animal;
import com.springboot.springtestx2.pojo.defenition.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BusinessPerson implements Person, BeanNameAware, BeanFactoryAware,
        ApplicationContextAware, InitializingBean, DisposableBean
{
    @Qualifier("animal_Cat")
    @Autowired
    private Animal animal;

    @Override
    public void service() {
        this.animal.use();
    }

    @Override
    @Autowired @Qualifier("animal_Cat")
    public void setAnimal(Animal animal) {
        System.out.println("LazyInit...");
        this.animal = animal;
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("BeanNameAware "+s);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware "+beanFactory.getBean(BusinessPerson.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware "+ applicationContext.getBean(BusinessPerson.class));
    }

    @Override
    public void destroy() {
        System.out.println("DisposableBean");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("InitializingBean");
    }
}
