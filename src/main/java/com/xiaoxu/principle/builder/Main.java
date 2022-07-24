package com.xiaoxu.principle.builder;

public class Main {
    public static void main(String[] args) {
        HouseDirector houseDirector = new HouseDirector(new HighHouseBuilder("高高的房子"));
        houseDirector.makeHouse();
    }
}
