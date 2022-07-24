package com.xiaoxu.principle.facade;

public class Control {
    public Fan fan;
    public Light light;
    public Window window;
    public Tv tv;

    public Control(Fan fan, Light light, Window window, Tv tv) {
        this.fan = fan;
        this.light = light;
        this.window = window;
        this.tv = tv;
    }

    public void goHome() {
        System.out.println("--------回家--------");
        fan.open();
        light.open();
        window.open();
        tv.open();
    }

    public void goSleep() {
        System.out.println("--------睡觉--------");
        light.close();
        tv.pause();
    }

    public void outHome() {
        System.out.println("--------出门--------");
        fan.close();
        light.close();
        window.close();
        tv.close();
    }
}
