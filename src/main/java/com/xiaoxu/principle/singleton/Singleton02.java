package com.xiaoxu.principle.singleton;

/**
 * 饿汉式静态代码块
 */
public class Singleton02 {
    // 定义一个本类型的静态私有常量
    private static final Singleton02 INSTANCE;

    // 使用静态代码块进行实例化
    static {
        INSTANCE = new Singleton02();
    }

    // 私有化构造器
    private Singleton02() {

    }

    /**
     * 提供一个公共静态的方法返回实例
     *
     * @return Singleton01
     */
    public static Singleton02 getInstance() {
        return INSTANCE;
    }
}

