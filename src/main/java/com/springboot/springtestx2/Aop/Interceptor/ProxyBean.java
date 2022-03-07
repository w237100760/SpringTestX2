package com.springboot.springtestx2.Aop.Interceptor;

import com.springboot.springtestx2.Aop.Interceptor.definition.Interceptor;
import com.springboot.springtestx2.Aop.Interceptor.definition.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyBean implements InvocationHandler {
    private Object target;
    private Interceptor interceptor;

    private ProxyBean() {
    }
    //静态内部类实现
    private static final class InstanceHolder {
        static final ProxyBean instance = new ProxyBean();
    }

    public static ProxyBean getInstance(){
        return InstanceHolder.instance;
    }

/*    //懒汉模式
    private static volatile ProxyBean instance = null;

    public static ProxyBean getInstance(){
        if (instance == null){
            synchronized (ProxyBean.class){
                if (instance == null){
                    instance = new ProxyBean();
                }
            }
        }
        return instance;
    }*/

    public static Object getProxyBean(Object target, Interceptor interceptor){
//        ProxyBean proxyBean = ProxyBean.getInstance();
//        proxyBean.target = target;
//        proxyBean.interceptor = interceptor;
        ProxyBean proxyBean = new ProxyBean();
        proxyBean.target = target;
        proxyBean.interceptor = interceptor;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), proxyBean);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        boolean exceptionFlag = false;
        Invocation invocation = new Invocation(target, method, args);
        Object retObj = null;
        try {
            if (this.interceptor.before()){
                retObj = this.interceptor.around(invocation);
            } else {
                retObj = method.invoke(target, args);
            }
        } catch (Exception e){
            exceptionFlag = true;
        }
        this.interceptor.after();;
        if (exceptionFlag){
            this.interceptor.afterThrowing();
        } else {
            this.interceptor.afterReturning();
            return retObj;
        }
        return null;
    }
}
