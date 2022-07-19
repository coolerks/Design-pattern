package com.xiaoxu.principle.proxy.staticproxy;

public class Target implements Interface {
    @Override
    public void method() {
        System.out.println("这是一个方法");
    }
}
