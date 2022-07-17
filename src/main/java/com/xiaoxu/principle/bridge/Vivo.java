package com.xiaoxu.principle.bridge;

public class Xiaomi implements Function{
    @Override
    public void turnOn() {
        System.out.println("小米手机开机");
    }

    @Override
    public void turnOff() {
        System.out.println("小米手机关机");
    }

    @Override
    public void call() {
        System.out.println("小米手机打电话");
    }
}
