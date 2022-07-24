package com.xiaoxu.principle.template;

public class Main {
    public static void main(String[] args) {
        Wash wash = new WashBig("羽绒服");
        wash.start();
        Wash wash2 = new WashSmall("内裤");
        wash2.start();
    }
}
