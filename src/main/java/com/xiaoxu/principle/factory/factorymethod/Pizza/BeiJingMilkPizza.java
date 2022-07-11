package com.xiaoxu.principle.factory.factorymethod;

public class BeiJingMilkPizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("北京牛奶披萨 正在准备");
    }
}
