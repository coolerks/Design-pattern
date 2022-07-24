package com.xiaoxu.principle.decorator;

public class WhiteCoffee extends Coffee {
    public WhiteCoffee(int count) {
        super(count);
        introduce = "白" + introduce + "（20元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " = ");
        return 20 * count;
    }
}
