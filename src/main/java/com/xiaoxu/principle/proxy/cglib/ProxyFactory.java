package com.xiaoxu.principle.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyFactory implements MethodInterceptor {
    private Object myObject;

    public ProxyFactory(Object myObject) {
        this.myObject = myObject;
    }

    public Object getProxyInstance() {
        // 创建工具类
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(myObject.getClass());
        // 设置回调，MethodInterceptor实现类回调接口
        enhancer.setCallback(this);
        // 返回代理对象
        return enhancer.create();
    }

    // 调用被代理对象的方法
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("开始代理");
        Object invoke = method.invoke(myObject, objects);
        System.out.println("结束代理");
        return invoke;
    }
}
