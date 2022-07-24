package com.xiaoxu.principle.factory.absfactory;

import com.xiaoxu.principle.factory.factorymethod.Pizza.BeiJingChickenPizza;
import com.xiaoxu.principle.factory.factorymethod.Pizza.BeiJingMilkPizza;
import com.xiaoxu.principle.factory.factorymethod.Pizza.Pizza;

public class BeiJingPizzaFactory implements PizzaFactory {
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
