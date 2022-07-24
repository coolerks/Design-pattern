package com.xiaoxu.principle.factory.absfactory;

import java.util.Calendar;

public class PizzaStore {
    public static void main(String[] args) {
        System.out.println("抽象方法");
        OrderPizza orderPizza = new OrderPizza(new BeiJingPizzaFactory());
        orderPizza.orderPizza("chicken", "milk", "milk");
    }
}
