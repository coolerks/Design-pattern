package com.xiaoxu.principle.builder;

// 产品
public class House {
    // 这个属性是必须的
    private String name;
    // 地基
    private String foundation, wall, top;

    public String getName() {
        return name;
    }

    public House(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    public String getWall() {
        return wall;
    }

    public void setWall(String wall) {
        this.wall = wall;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", foundation='" + foundation + '\'' +
                ", wall='" + wall + '\'' +
                ", top='" + top + '\'' +
                '}';
    }
}
