package com.xiaoxu.principle.template;

// 洗小衣服
public class WashSmall extends Wash {

    public WashSmall(String close) {
        super(close);
    }

    @Override
    public void washMode() {
        System.out.println("设置为小衣服模式");
    }
}
