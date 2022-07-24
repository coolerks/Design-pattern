package com.xiaoxu.principle.proxy.staticproxy;

public class Main {
    public static void main(String[] args) {
        Proxy proxy = new Proxy(new Target());
        proxy.method();
    }
}
