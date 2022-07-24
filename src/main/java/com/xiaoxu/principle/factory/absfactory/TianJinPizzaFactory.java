package com.xiaoxu.principle.factory.absfactory;

import com.xiaoxu.principle.factory.factorymethod.Pizza.Pizza;
import com.xiaoxu.principle.factory.factorymethod.Pizza.TianJinChickenPizza;
import com.xiaoxu.principle.factory.factorymethod.Pizza.TianJinMilkPizza;

public class TianJinPizzaFactory implements PizzaFactory {
    @Override
    public Pizza createPizza(String name) {
        Pizza pizza = null;
        if ("milk".equals(name)) {
            pizza = new TianJinMilkPizza();
            pizza.setName("牛奶披萨");
        } else if ("chicken".equals(name)) {
            pizza = new TianJinChickenPizza();
            pizza.setName("鸡肉披萨");
        }
        return pizza;
    }
}
