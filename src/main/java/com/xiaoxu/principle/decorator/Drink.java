package com.xiaoxu.principle.decorator;

public abstract class Drink {
    protected String introduce;
    protected int count = 1;

    public abstract int cost();

    public String getIntroduce() {
        return this.introduce;
    }

    public Drink() {

    }

    public Drink(int count) {
        this.count = count;
    }
}
