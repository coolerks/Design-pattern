package com.xiaoxu.principle.factory.absfactory;

import com.xiaoxu.principle.factory.factorymethod.Pizza.Pizza;

public class OrderPizza {
    private PizzaFactory simplePizzaFactory;

    public void setSimplePizzaFactory(PizzaFactory simplePizzaFactory) {
        this.simplePizzaFactory = simplePizzaFactory;
    }

    public OrderPizza(PizzaFactory simplePizzaFactory) {
        this.simplePizzaFactory = simplePizzaFactory;
    }

    public void orderPizza(String... pizzas) {
        for (String s : pizzas) {
            Pizza pizza = simplePizzaFactory.createPizza(s);
            if (pizza != null) {
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            }
        }
    }

}
