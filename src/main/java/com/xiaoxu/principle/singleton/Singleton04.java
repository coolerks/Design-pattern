package com.xiaoxu.principle.singleton;


/**
 * 懒汉式 线程安全
 */
public class Singleton04 {
    // 定义一个本类型的静态私有常量
    private static Singleton04 INSTANCE;


    // 私有化构造器
    private Singleton04() {

    }

    /**
     * 提供一个公共静态同步的方法返回实例
     *
     * @return Singleton01
     */
    public static synchronized Singleton04 getInstance() {
        // 如果是空的就进行实例化
        if (INSTANCE == null) {
            INSTANCE = new Singleton04();
        }
        return INSTANCE;
    }
}
