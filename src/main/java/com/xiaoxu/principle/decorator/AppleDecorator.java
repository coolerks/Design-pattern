package com.xiaoxu.principle.decorator;

// 装饰者
public class AppleDecorator extends Decorator{

    public AppleDecorator(Drink drink) {
        super(drink);
        introduce = "苹果配料（1元）";
    }

    public AppleDecorator(Drink drink, int count) {
        super(drink, count);
        introduce = "苹果配料（1元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " + ");
        return count * 1 + drink.cost();
    }
}
