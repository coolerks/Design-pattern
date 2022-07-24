package com.xiaoxu.principle.factory.simplefactory;

public class PizzaStore {
    public static void main(String[] args) {
        OrderPizza orderPizza = new OrderPizza(new SimplePizzaFactory());
        orderPizza.orderPizza("chicken", "milk", "milk");
    }
}
