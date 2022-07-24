package com.xiaoxu.principle.bridge;

public class Main {
    public static void main(String[] args) {
        Phone phone = new FoldablePhone(new Xiaomi());
        phone.call();
        phone.turnOff();
        phone.turnOn();
    }
}
