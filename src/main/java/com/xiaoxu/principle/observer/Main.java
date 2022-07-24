package com.xiaoxu.principle.observer;

public class Main {
    public static void main(String[] args) {
        Weather weather = new Weather();

        Observer company = new Company();
        Observer user = new User();

        weather.registerObserver(company);
        weather.registerObserver(user);

        weather.changeData(34, 3, "好天气");
        weather.changeData(334, 23, "不太好天气");
        weather.changeData(2434, 23, "坏天气");
    }
}
