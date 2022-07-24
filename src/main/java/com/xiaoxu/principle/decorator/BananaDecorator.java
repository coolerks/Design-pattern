package com.xiaoxu.principle.decorator;

// 装饰者
public class BananaDecorator extends Decorator{

    public BananaDecorator(Drink drink) {
        super(drink);
        introduce = "香蕉配料（3元）";
    }

    public BananaDecorator(Drink drink, int count) {
        super(drink, count);
        introduce = "香蕉配料（3元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " + ");
        return count * 3 + drink.cost();
    }
}
