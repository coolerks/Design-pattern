package com.xiaoxu.principle.memento;

import java.util.HashMap;

// 管理存储的内容
public class MementoMananger {
    private HashMap<String, Memento> map = new HashMap<>();

    public void save(String key, Memento m) {
        map.put(key, m);
    }

    public Memento getMemento(String key) {
        return map.get(key);
    }
}
