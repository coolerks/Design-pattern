package com.xiaoxu.principle.decorator;

public abstract class Coffee extends Drink {
    public Coffee(int count) {
        introduce = "咖啡";
        this.count = count;
    }


}
