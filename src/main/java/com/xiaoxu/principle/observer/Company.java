package com.xiaoxu.principle.observer;

public class User implements Observer{
    @Override
    public void update(String msg) {
        System.out.println("收到天气更新，更新内容为：" + msg);
    }
}
