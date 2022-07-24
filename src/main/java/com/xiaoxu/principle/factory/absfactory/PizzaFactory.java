package com.xiaoxu.principle.factory.absfactory;

import com.xiaoxu.principle.factory.factorymethod.Pizza.Pizza;

public interface PizzaFactory {
    Pizza createPizza(String name);
}
