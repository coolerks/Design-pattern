package com.xiaoxu.principle.strategy;

public class Duck {
    private Fly fly;

    public Duck(Fly fly) {
        this.fly = fly;
    }

    public void fly() {
        System.out.print("普通鸭子，");
        fly.fly();
    }
}
