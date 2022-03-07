package com.springboot.springtestx2.Aop.Interceptor.definition;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
public class Invocation {
    private Object[] params;
    private Method method;
    private Object target;

    public Invocation(Object target, Method method, Object[] params) {
        this.params = params;
        this.method = method;
        this.target = target;
    }

    public Object proceed()
            throws InvocationTargetException, IllegalAccessException{
        return method.invoke(target, params);
    }
}
