package com.xiaoxu.principle.proxy.dynamic;

import java.lang.reflect.Proxy;

public class ProxyFactory {
    private Interface in;

    public ProxyFactory(Interface in) {
        this.in = in;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                in.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    if ("method2".equals(method.getName())) {
                        System.out.println("即将代理第二个方法");
                    } else {
                        System.out.println("代理其他方法");
                    }
                    System.out.println("动态代理开始");
                    Object invoke = method.invoke(in, args);
                    System.out.println("动态代理结束");
                    return invoke;
                });
    }
}
