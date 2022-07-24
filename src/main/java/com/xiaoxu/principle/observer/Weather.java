package com.xiaoxu.principle.observer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Weather implements Subject {
    // 温度 风力
    private int temperature, windPower;
    // 天气建议
    private String advice;

    @Override
    public String toString() {
        return "Weather{" +
                "temperature=" + temperature +
                ", windPower=" + windPower +
                ", advice='" + advice + '\'' +
                '}';
    }

    private List<Observer> observers = new LinkedList<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        System.out.println("开始通知所有的观察者更新天气情况：");
        observers.forEach(it -> it.update(toString()));
        System.out.println();
    }

    public void changeData(int temperature, int windPower, String advice) {
        this.temperature = temperature;
        this.windPower = temperature;
        this.advice = advice;
        notifyObservers();
    }
}
