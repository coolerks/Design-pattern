package com.xiaoxu.principle.decorator;

// 装饰者
public class MilkDecorator extends Decorator{

    public MilkDecorator(Drink drink) {
        super(drink);
        introduce = "牛奶配料（2元）";
    }

    public MilkDecorator(Drink drink, int count) {
        super(drink, count);
        introduce = "牛奶配料（2元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " + ");
        return count * 2 + drink.cost();
    }
}
