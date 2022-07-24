package com.xiaoxu.principle.singleton;


/**
 * 懒汉式 双重检查
 */
public class Singleton06 {
    // 定义一个本类型的静态私有常量
    private static volatile Singleton06 INSTANCE;


    // 私有化构造器
    private Singleton06() {

    }

    /**
     * 提供一个公共静态同步的方法返回实例
     *
     * @return Singleton01
     */
    public static Singleton06 getInstance() {
        // 如果是空的就进行实例化
        if (INSTANCE == null) {
            synchronized (Singleton06.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton06();
                }
            }
        }
        return INSTANCE;
    }
}
