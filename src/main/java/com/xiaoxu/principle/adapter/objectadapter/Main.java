package com.xiaoxu.principle.adapter.classadapter;

public class Main {
    public static void main(String[] args) {
        Computer computer = new Computer(new MouseAdapter(new Mouse()));
        computer.usb.insert();
    }
}
