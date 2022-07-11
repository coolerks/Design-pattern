package com.xiaoxu.principle.factory.factorymethod;

import com.xiaoxu.principle.factory.factorymethod.Pizza.Pizza;
import com.xiaoxu.principle.factory.factorymethod.Pizza.TianJinChickenPizza;
import com.xiaoxu.principle.factory.factorymethod.Pizza.TianJinMilkPizza;

public abstract class PizzaFactory {
    public abstract Pizza createPizza(String name);
}
