package com.xiaoxu.principle.singleresponsibility;

/**
 * 单一职责
 * @author xiaoxu
 */
public class Main {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle();
        vehicle.run("汽车");
        vehicle.run("自行车");
        vehicle.run("飞机");
    }
}

/**
 * 交通工具类
 */
class Vehicle {
    public void run(String name) {
        System.out.println(name + "在路上运行...");
    }
}
