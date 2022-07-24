package com.xiaoxu.principle.observer;

public class Company implements Observer{
    @Override
    public void update(String msg) {
        System.out.println("公司收到天气更新，更新内容为：" + msg);
    }
}
