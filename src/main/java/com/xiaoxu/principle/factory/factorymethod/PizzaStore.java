package com.xiaoxu.principle.factory.factorymethod;

public class PizzaStore {
    public static void main(String[] args) {
        OrderPizza orderPizza = new OrderPizza(new TianJinPizzaFactory());
        orderPizza.orderPizza("chicken", "milk", "milk");
    }
}
