package com.xiaoxu.principle.factory.simplefactory;

public class SimplePizzaFactory {
    public Pizza createPizza(String name) {
        Pizza pizza = null;
        if ("milk".equals(name)) {
            pizza = new MilkPizza();
            pizza.setName("牛奶披萨");
        } else if ("chicken".equals(name)) {
            pizza = new ChickenPizza();
            pizza.setName("鸡肉披萨");
        }

        return pizza;
    }
}
