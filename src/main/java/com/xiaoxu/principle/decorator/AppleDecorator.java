package com.xiaoxu.principle.decorator;

// 装饰者
public class MilkDecorator extends Decorator{

    public MilkDecorator(Drink drink) {
        super(drink);
    }

    @Override
    public int cost() {
        return count * 2 + drink.cost();
    }
}
