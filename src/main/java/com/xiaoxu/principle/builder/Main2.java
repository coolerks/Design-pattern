package com.xiaoxu.principle.builder;

public class Main2 {
    public static void main(String[] args) {
        House2 house2 = new House2.Builder("大大的房子")
                .setFoundation("大大的地基")
                .setTop("大大的顶部")
                .setWall("大大的墙")
                .build();
        System.out.println("house2 = " + house2);
    }
}
