package com.xiaoxu.principle.singleton;


/**
 * 懒汉式 线程安全
 */
public class Singleton05 {
    // 定义一个本类型的静态私有常量
    private static Singleton05 INSTANCE;


    // 私有化构造器
    private Singleton05() {

    }

    /**
     * 提供一个公共静态同步的方法返回实例
     *
     * @return Singleton01
     */
    public static Singleton05 getInstance() {
        // 如果是空的就进行实例化
        if (INSTANCE == null) {
            synchronized (Singleton05.class) {
                INSTANCE = new Singleton05();
            }
        }
        return INSTANCE;
    }
}
