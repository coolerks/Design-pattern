package com.xiaoxu.principle.factory.simplefactory;

public class ChickenPizza extends Pizza{

    @Override
    public void prepare() {
        System.out.println("鸡肉披萨 正在准备");
    }
}
