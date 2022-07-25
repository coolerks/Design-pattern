package com.xiaoxu.principle.strategy;

public class ToyDuck {
    private Fly fly;

    public ToyDuck(Fly fly) {
        this.fly = fly;
    }

    public void fly() {
        System.out.print("玩具鸭子，");
        fly.fly();
    }
}
