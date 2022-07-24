package com.xiaoxu.principle.factory.factorymethod.Pizza;

public class TianJinMilkPizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("天津牛奶披萨 正在准备");
    }
}
