package com.xiaoxu.principle.factory.factorymethod;

public abstract class Pizza {
    public String name;

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 准备原材料，不同的披萨原材料不同
     */
    public abstract void prepare();

    public void bake() {
        System.out.println("烘烤披萨");
    }

    public void cut() {
        System.out.println("切披萨");
    }

    public void box() {
        System.out.println("打包披萨");
    }
}
