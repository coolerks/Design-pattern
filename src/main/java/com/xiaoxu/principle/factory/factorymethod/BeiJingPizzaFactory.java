package com.xiaoxu.principle.factory.factorymethod;

import com.xiaoxu.principle.factory.factorymethod.Pizza.*;

public class BeiJingPizzaFactory extends PizzaFactory {
    @Override
    public Pizza createPizza(String name) {
        Pizza pizza = null;
        if ("milk".equals(name)) {
            pizza = new BeiJingMilkPizza();
            pizza.setName("牛奶披萨");
        } else if ("chicken".equals(name)) {
            pizza = new BeiJingChickenPizza();
            pizza.setName("鸡肉披萨");
        }
        return pizza;
    }
}
