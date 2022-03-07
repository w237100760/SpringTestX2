package com.springboot.springtestx2.Aop.Interceptor;

import com.springboot.springtestx2.Aop.Interceptor.definition.Interceptor;
import com.springboot.springtestx2.Aop.Interceptor.definition.Invocation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyBean implements InvocationHandler {
    private Object target;//被代理的对象
    private Interceptor interceptor;//拦截器

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

    /**
     * @param target 被代理对象
     * @param interceptor 拦截代理对象的拦截器
     * @return 返回代理实例
     */
    public static Object getProxyBean(Object target, Interceptor interceptor){
        ProxyBean proxyBean = new ProxyBean();
        //将代理类传给调用请求处理器，处理所有的代理对象上的方法调用
        proxyBean.target = target;
        proxyBean.interceptor = interceptor;
        //由target的ClassLoader和Interfaces确定的代理实例，并交给调用逻辑处理器ProxyBean实现功能增强
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), proxyBean);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        boolean exceptionFlag = false;
        Invocation invocation = new Invocation(target, method, args);//保存target 代理对象，method 代理对象的方法，args 代理对象的运行参数
        Object retObj = null;
        try {
            if (this.interceptor.before()){
                retObj = this.interceptor.around(invocation);//method.invoke(target, args);
            } else {
                retObj = method.invoke(target, args);
            }
        } catch (Exception e){
            exceptionFlag = true;
        }
        this.interceptor.after();
        if (exceptionFlag){
            this.interceptor.afterThrowing();
        } else {
            this.interceptor.afterReturning();
            return retObj;
        }
        return null;
    }
}
