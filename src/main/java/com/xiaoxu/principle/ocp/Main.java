package com.xiaoxu.principle.ocp;


/**
 * 开闭原则
 */
public class Main {
    public static void main(String[] args) {
        Sharp triangle = new Triangle();
        Sharp rectangle = new Rectangle();
        triangle.draw();
        rectangle.draw();
    }
}

abstract class Sharp {
    public int type;
    public abstract void draw();
}

// 三角形
class Triangle extends Sharp {
    public Triangle() {
        type = 1;
    }

    @Override
    public void draw() {
        System.out.println("画三角形");
    }
}
// 长方形
class Rectangle extends Sharp {
    public Rectangle() {
        type = 2;
    }

    @Override
    public void draw() {
        System.out.println("画长方形");
    }
}
