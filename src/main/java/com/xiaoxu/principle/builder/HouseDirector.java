package com.xiaoxu.principle.builder;

// 指挥者
public class HouseDirector {
    HouseBuilder houseBuilder;

    // 聚合建造者
    public HouseDirector(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    // 建造房子，里边的具体步骤可以自定义
    public void makeHouse() {
        houseBuilder.foundationBuild();
        houseBuilder.wallBuild();
        houseBuilder.topBuild();
        House house = houseBuilder.build();
        System.out.println(house);
    }
}
