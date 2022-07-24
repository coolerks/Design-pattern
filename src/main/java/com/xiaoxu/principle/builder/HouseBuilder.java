package com.xiaoxu.principle.builder;

public abstract class HouseBuilder {
    // 聚合House
    protected House house;

    public HouseBuilder(String name) {
        this.house = new House(name);
    }

    public abstract void foundationBuild();

    public abstract void topBuild();

    public abstract void wallBuild();

    public House build() {
        return house;
    }


}
