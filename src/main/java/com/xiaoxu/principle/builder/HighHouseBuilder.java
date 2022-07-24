package com.xiaoxu.principle.builder;

// 高房子
public class HighHouseBuilder extends HouseBuilder {
    public HighHouseBuilder(String name) {
        super(name);
    }

    @Override
    public void foundationBuild() {
        System.out.println("高房子盖地基");
        house.setFoundation("高房子的地基");
    }

    @Override
    public void topBuild() {
        System.out.println("高房子封顶");
        house.setFoundation("高房子的封顶");
    }

    @Override
    public void wallBuild() {
        System.out.println("高房子盖墙");
        house.setFoundation("高房子的墙");
    }
}
