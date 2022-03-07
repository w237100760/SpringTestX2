package com.springboot.springtestx2.Aop.Interceptor.definition;

import java.lang.reflect.InvocationTargetException;

public interface Interceptor {

    boolean before();

    void after();

    Object around(Invocation invocation) throws InvocationTargetException, IllegalAccessException;

    void afterReturning();

    void afterThrowing();

    boolean useAround();
}
