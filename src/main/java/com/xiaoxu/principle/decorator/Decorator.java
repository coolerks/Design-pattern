package com.xiaoxu.principle.decorator;

// 装饰者
public abstract class Decorator extends Drink{
    protected Drink drink;

    public Decorator(Drink drink) {
        this.drink = drink;
    }

    public Decorator(Drink drink, int count) {
        super(count);
        this.drink = drink;
    }

}
