package com.xiaoxu.principle.builder;

public class House2 {
    // 这个属性是必须的
    private String name;
    // 地基
    private String foundation, wall, top;

    @Override
    public String toString() {
        return "House2{" +
                "name='" + name + '\'' +
                ", foundation='" + foundation + '\'' +
                ", wall='" + wall + '\'' +
                ", top='" + top + '\'' +
                '}';
    }

    private House2(Builder builder) {
        this.name = builder.name;
        this.foundation = builder.foundation;
        this.wall = builder.wall;
        this.top = builder.top;
    }

    public static class Builder {
        // 这个属性是必须的
        private String name;
        // 地基
        private String foundation, wall, top;


        // 为必须的参数赋值
        public Builder(String name) {
            this.name = name;
        }

        public House2.Builder setFoundation(String foundation) {
            this.foundation = foundation;
            return this;
        }

        public House2.Builder setWall(String wall) {
            this.wall = wall;
            return this;
        }

        public House2.Builder setTop(String top) {
            this.top = top;
            return this;
        }

        public House2 build() {
            return new House2(this);
        }
    }
}
