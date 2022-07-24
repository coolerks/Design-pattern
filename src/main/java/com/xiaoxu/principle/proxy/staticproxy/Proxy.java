package com.xiaoxu.principle.proxy.staticproxy;

public class Proxy implements Interface {
    public Proxy(Interface in) {
        this.in = in;
    }

    private final Interface in;

    @Override
    public void method() {
        System.out.println("代理之前");
        in.method();
        System.out.println("代理之后");
    }
}
