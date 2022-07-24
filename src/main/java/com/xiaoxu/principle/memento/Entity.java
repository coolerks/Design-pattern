package com.xiaoxu.principle.memento;

public class Entity {
    // 用来表示某个属性，这个属性需要存储
    private String attribute;
    // 无关紧要的属性
    public String variable;

    public String getAttribute() {
        return attribute;
    }

    public void setAttrbute(String attrbute) {
        this.attribute = attrbute;
    }

    // 存储需要存储的内容
    public Memento save() {
        return new Memento(attribute);
    }

    // 恢复内容
    public void recoveryFromMemento(Memento memento) {
        this.attribute = memento.content;
    }
}
