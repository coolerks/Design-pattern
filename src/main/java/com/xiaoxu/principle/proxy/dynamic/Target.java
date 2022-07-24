package com.xiaoxu.principle.proxy.dynamic;

public class Target implements Interface {
    @Override
    public void method() {
        System.out.println("这是一个方法");
    }

    @Override
    public void method2() {
        System.out.println("这是method2");
    }
}
