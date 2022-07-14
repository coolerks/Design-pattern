package com.xiaoxu.principle.adapter.classadapter;

public class MouseAdapter implements USB {
    Mouse mouse;

    public MouseAdapter(Mouse mouse) {
        this.mouse = mouse;
    }

    @Override
    public void insert() {
        System.out.println("鼠标已插入");
        mouse.useMouse();
    }

    @Override
    public void send() {
        System.out.println("鼠标发送信号");
    }
}
