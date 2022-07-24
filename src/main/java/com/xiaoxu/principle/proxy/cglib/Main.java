package com.xiaoxu.principle.proxy.cglib;

public class Main {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new MyClass());
        MyClass proxy = (MyClass) proxyFactory.getProxyInstance();
        proxy.method();
    }
}
