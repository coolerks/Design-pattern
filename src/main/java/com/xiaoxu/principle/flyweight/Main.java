package com.xiaoxu.principle.flyweight;

public class Main {
    public static void main(String[] args) {
        Factory factory = new Factory();
        Chess blackChess = factory.getChess("黑棋");
        blackChess.use();
        Chess blackChess2 = factory.getChess("黑棋");
        blackChess2.use();
        Chess whiteChess = factory.getChess("白棋");
        whiteChess.use();
    }
}
