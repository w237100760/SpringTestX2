package com.springboot.springtestx2.Aop;

import com.springboot.springtestx2.Aop.Interceptor.ProxyBean;
import com.springboot.springtestx2.Aop.Interceptor.myInterceptor;
import com.springboot.springtestx2.Aop.Service.HelloService;
import com.springboot.springtestx2.Aop.Service.impl.HelloServiceImpl;


public class AopTest {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        //将服务类和拦截方法织入，生成代理类实例
        HelloService proxy = (HelloService) ProxyBean.getProxyBean(helloService, new myInterceptor());
        proxy.sayHello("kkk");
    }
}

/*
* JDK动态代理详解： https://zhuanlan.zhihu.com/p/347141071
*
* 1.实现InvocationHandler接口,创建调用请求处理器
* 2.创建被代理的对象
* 3.根据2获取其ClassLoader和所有接口的Class(即不同Interface生成的.Class文件)
* 4.调用newInstance()创建代理实例 (需要1的调用请求处理器,3的类加载器和类文件)
*
* proxy.sayHello("kkk");会通过ProxyBean的invoke方法去调用, 最终使用的是method.invoke(target, args);
* */