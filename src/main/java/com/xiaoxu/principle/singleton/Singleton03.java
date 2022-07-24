package com.xiaoxu.principle.singleton;

/**
 * 懒汉式 线程不安全
 */
public class Singleton03 {
    // 定义一个本类型的静态私有常量
    private static Singleton03 INSTANCE;


    // 私有化构造器
    private Singleton03() {

    }

    /**
     * 提供一个公共静态的方法返回实例
     *
     * @return Singleton01
     */
    public static Singleton03 getInstance() {
        // 如果是空的就进行实例化
        if (INSTANCE == null) {
            INSTANCE = new Singleton03();
        }
        return INSTANCE;
    }
}
