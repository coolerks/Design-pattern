package com.xiaoxu.principle.bridge;

public class Vivo implements Function{
    @Override
    public void turnOn() {
        System.out.println("Vivo手机开机");
    }

    @Override
    public void turnOff() {
        System.out.println("Vivo手机关机");
    }

    @Override
    public void call() {
        System.out.println("Vivo手机打电话");
    }
}
