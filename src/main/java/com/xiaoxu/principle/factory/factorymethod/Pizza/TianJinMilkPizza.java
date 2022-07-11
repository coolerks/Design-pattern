package com.xiaoxu.principle.factory.factorymethod;

public class MilkPizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("牛奶披萨 正在准备");
    }
}
