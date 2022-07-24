package com.xiaoxu.principle.facade;

// 外观模式
public class Main {
    public static void main(String[] args) {
        Control control = new Control(new Fan(), new Light(), new Window(), new Tv());
        control.goHome();
        control.goSleep();
        control.outHome();
    }
}
