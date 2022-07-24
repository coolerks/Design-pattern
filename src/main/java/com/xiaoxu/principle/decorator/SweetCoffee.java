package com.xiaoxu.principle.decorator;

public class SweetCoffee extends Coffee {
    public SweetCoffee(int count) {
        super(count);
        introduce = "甜" + introduce + "（15元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " = ");
        return 15 * count;
    }
}
