package com.xiaoxu.principle.singleton;

/**
 * 饿汉式静态常量
 */
public class Singleton01 {
    // 实例化一个本类型的静态私有常量
    private static final Singleton01 INSTANCE = new Singleton01();

    // 私有化构造器
    private Singleton01() {

    }

    /**
     * 提供一个公共静态的方法返回实例
     * @return Singleton01
     */
    public static Singleton01 getInstance() {
        return INSTANCE;
    }
}
