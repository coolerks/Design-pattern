package com.xiaoxu.principle.strategy;

public class CanFly implements Fly{
    @Override
    public void fly() {
        System.out.println("这个鸭鸭会飞");
    }
}
