package com.xiaoxu.principle.adapter.objectadapter;

public class Main {
    public static void main(String[] args) {
        Computer computer = new Computer(new MouseAdapter(new Mouse()));
        computer.usb.insert();
    }
}
