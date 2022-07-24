package com.xiaoxu.principle.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
//        Singleton03 instance = Singleton03.getInstance();
//        System.out.println("instance = " + instance);
//        System.out.println("Singleton03.getInstance() = " + Singleton03.getInstance());
        System.out.println("Runtime.getRuntime() = " + Runtime.getRuntime());
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        for (int i = 0; i < 100; i++) {
//            executorService.execute(() -> {
//                System.out.println("Singleton08..INSTANCE = " + Singleton08.INSTANCE.hashCode());
//            });
//        }
    }
}
