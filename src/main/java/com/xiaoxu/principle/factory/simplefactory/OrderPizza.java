package com.xiaoxu.principle.factory.simplefactory;

public class OrderPizza {
    private SimplePizzaFactory simplePizzaFactory;

    public void setSimplePizzaFactory(SimplePizzaFactory simplePizzaFactory) {
        this.simplePizzaFactory = simplePizzaFactory;
    }

    public OrderPizza(SimplePizzaFactory simplePizzaFactory) {
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
