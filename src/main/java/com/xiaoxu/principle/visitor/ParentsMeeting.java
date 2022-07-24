package com.xiaoxu.principle.visitor;

import java.util.ArrayList;
import java.util.List;

// 充当数据结构ObjectStructure的作用
public class ParentsMeeting {
    private List<Persion> list = new ArrayList<>();

    public void addPersion(Persion persion) {
        list.add(persion);
    }

    public void removeAll() {
        list.clear();
    }

    public void startVisit(Visitor visitor) {
        list.forEach(it -> it.accept(visitor));
    }
}
