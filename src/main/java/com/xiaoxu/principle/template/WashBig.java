package com.xiaoxu.principle.template;

// 洗小衣服
public class WashBig extends Wash {

    public WashBig(String close) {
        super(close);
    }

    @Override
    public void washMode() {
        System.out.println("设置为大衣服模式");
    }
}
