package com.springboot.springtestx2.Aop.Interceptor;

import com.springboot.springtestx2.Aop.Interceptor.definition.Interceptor;
import com.springboot.springtestx2.Aop.Interceptor.definition.Invocation;

import java.lang.reflect.InvocationTargetException;

public class myInterceptor implements Interceptor {
    @Override
    public boolean before() {
        System.out.println("before...");
        return true;
    }

    @Override
    public void after() {
        System.out.println("after...");
    }

    @Override
    public Object around(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        return invocation.proceed();
    }

    @Override
    public void afterReturning() {
        System.out.println("afterReturning...");
    }

    @Override
    public void afterThrowing() {
        System.out.println("afterThrowing...");
    }

    @Override
    public boolean useAround() {
        return true;
    }
}
