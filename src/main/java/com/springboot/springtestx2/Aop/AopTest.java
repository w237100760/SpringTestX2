package com.springboot.springtestx2.Aop;

import com.springboot.springtestx2.Aop.Interceptor.ProxyBean;
import com.springboot.springtestx2.Aop.Interceptor.myInterceptor;
import com.springboot.springtestx2.Aop.Service.HelloService;
import com.springboot.springtestx2.Aop.Service.impl.HelloServiceImpl;


public class AopTest {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        //将服务类和拦截方法织入对应的流程
        HelloService proxy = (HelloService) ProxyBean.getProxyBean(helloService, new myInterceptor());
        proxy.sayHello("kkk");
    }
}
