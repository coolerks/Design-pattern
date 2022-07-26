package com.xiaoxu.principle.chain;

public class Request {
    public String name;
    public int price;

    public Request(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
