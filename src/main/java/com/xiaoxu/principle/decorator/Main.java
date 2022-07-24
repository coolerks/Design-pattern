package com.xiaoxu.principle.decorator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {

        System.out.println(new AppleDecorator(new BananaDecorator(new BlackCoffee(1))).cost());
        System.out.println(new BananaDecorator(new AppleDecorator(new MilkDecorator(new WhiteCoffee(2)), 3)).cost());
    }
}
