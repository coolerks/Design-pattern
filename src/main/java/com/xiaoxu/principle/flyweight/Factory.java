package com.xiaoxu.principle.flyweight;

import java.util.HashMap;

public class Factory {
    private HashMap<String, WhiteAndBlackChess> map = new HashMap<>();

    public Chess getChess(String name) {
        if (map.containsKey(name)) {
            return map.get(name);
        }
        WhiteAndBlackChess value = new WhiteAndBlackChess();
        map.put(name, value);
        System.out.println("创建" + name + "实例");
        return value;
    }
}
