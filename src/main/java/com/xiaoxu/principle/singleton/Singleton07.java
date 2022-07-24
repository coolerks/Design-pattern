package com.xiaoxu.principle.singleton;

import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * 饿汉式静态常量
 */
public class Singleton07 {
    // 私有化构造器
    private Singleton07() {

    }

    // 私有化静态内部类
    private static class SingleInstance {
        // 实例化一个外部类的静态私有常量
        private static final Singleton07 INSTANCE = new Singleton07();
    }

    /**
     * 提供一个公共静态的方法返回实例
     *
     * @return Singleton07
     */
    public static Singleton07 getInstance() {
        // 返回静态内部类的静态私有常量
        return SingleInstance.INSTANCE;
    }
}