package com.springboot.springtestx2.Aop;

import com.springboot.springtestx2.Aop.Service.NameValidator;
import com.springboot.springtestx2.Aop.Service.impl.NameValidatorImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class MyAspect {
    /*HelloServiceImpl 增强 NameValidatorImpl的实现方法*/
    @DeclareParents(value = "com.springboot.springtestx2.Aop.Service.impl.HelloServiceImpl",
    defaultImpl = NameValidatorImpl.class)
    public NameValidator nameValidator;

    @Pointcut("execution(* com.springboot.springtestx2.Aop.Service.impl.HelloServiceImpl.sayHello(..))")
    public void pointCut(){
    }

    @Before("pointCut() && args(name)")
    public void before(JoinPoint point, String name){
        System.out.println("before..."+name);
    }

    @After("pointCut()")
    public void after(){
        System.out.println("after...");
    }

    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("afterReturning...");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("afterThrowing...");
    }
}
