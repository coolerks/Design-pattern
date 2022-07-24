package com.xiaoxu.principle.bridge;

public abstract class Phone {
    public Phone(Function function) {
        this.function = function;
    }

    private final Function function;

    public void turnOn() {
        function.turnOn();
    }

    public void turnOff() {
        function.turnOn();
    }

    public void call() {
        function.call();
    }
}
