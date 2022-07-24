package com.xiaoxu.principle.proxy.dynamic;

// 动态代理
public class Main {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new Target());
        Interface proxyInstance = (Interface) proxyFactory.getProxyInstance();
        proxyInstance.method2();
    }
}
