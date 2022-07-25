package com.xiaoxu.principle.strategy;

public class NoFly implements Fly{
    @Override
    public void fly() {
        System.out.println("这个鸭鸭不会飞");
    }
}
