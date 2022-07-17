package com.xiaoxu.principle.decorator;

// 装饰者
public class AppleDecorator extends Decorator{

    public AppleDecorator(Drink drink) {
        super(drink);
    }

    @Override
    public int cost() {
        return count * 1 + drink.cost();
    }
}
