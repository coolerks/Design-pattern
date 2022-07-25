package com.xiaoxu.principle.strategy;

public class Main {
    public static void main(String[] args) {
        Duck duck = new Duck(new CanFly());
        duck.fly();

        ToyDuck toyDuck = new ToyDuck(new NoFly());
        toyDuck.fly();
    }
}
