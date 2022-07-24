package com.xiaoxu.principle.builder;

// 普通房子
public class OrdinaryHouseBuilder extends HouseBuilder {
    public OrdinaryHouseBuilder(String name) {
        super(name);
    }

    @Override
    public void foundationBuild() {
        System.out.println("普通房子盖地基");
        house.setFoundation("普通房子的地基");
    }

    @Override
    public void topBuild() {
        System.out.println("普通房子封顶");
        house.setFoundation("普通房子的封顶");
    }

    @Override
    public void wallBuild() {
        System.out.println("普通房子盖墙");
        house.setFoundation("普通房子的墙");
    }
}
