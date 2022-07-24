package com.xiaoxu.principle.bridge;

// 直立式
public class ErectPositionPhone extends Phone{
    public ErectPositionPhone(Function function) {
        super(function);
    }

    @Override
    public void call() {
        System.out.print("直立式");
        super.call();
    }

    @Override
    public void turnOff() {
        System.out.print("直立式");
        super.turnOff();
    }

    @Override
    public void turnOn() {
        System.out.print("直立式");
        super.turnOn();
    }
}
