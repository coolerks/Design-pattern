package com.xiaoxu.principle.template;

public abstract class Wash {
    public String close;

    public Wash(String close) {
        this.close = close;
    }

    // 洗的方式
    public abstract void washMode();

    // 烘干
    public void dry() {
        System.out.println("开始烘干衣服");
    }

    // 定时
    public void timing() {
        System.out.println("开始定时");
    }

    // 放入衣服
    public void add() {
        System.out.println("放入衣服");
    }

    // 结束洗衣服
    public void stop() {
        System.out.println("结束");
    }

    public final void start() {
        System.out.println("------" + this.close + "------");
        add();
        washMode();
        timing();
        stop();
    }
}
