package com.xiaoxu.principle.bridge;

// 折叠
public class FoldablePhone extends Phone{
    public FoldablePhone(Function function) {
        super(function);
    }

    @Override
    public void call() {
        System.out.print("折叠式");
        super.call();
    }

    @Override
    public void turnOff() {
        System.out.print("折叠式");
        super.turnOff();
    }

    @Override
    public void turnOn() {
        System.out.print("折叠式");
        super.turnOn();
    }
}
