package com.xiaoxu.principle.decorator;

public class BlackCoffee extends Coffee{
    public BlackCoffee(int count) {
        super(count);
        introduce = "黑" + introduce + "（10元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " = ");
        return 10 * count;
    }
}
