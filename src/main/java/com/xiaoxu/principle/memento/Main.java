package com.xiaoxu.principle.memento;

// 备忘录模式
public class Main {
    public static void main(String[] args) {
        Entity entity = new Entity();
        entity.setAttrbute("这是一个参数");

        MementoMananger mementoMananger = new MementoMananger();
        mementoMananger.save("first", entity.save());

        entity.setAttrbute("改变参数");
        mementoMananger.save("second", entity.save());
        entity.setAttrbute("改变参数222");
        entity.setAttrbute("改变参数333");
        mementoMananger.save("third", entity.save());

        // 恢复到第一次
        entity.recoveryFromMemento(mementoMananger.getMemento("first"));
        System.out.println("entity.getAttribute() = " + entity.getAttribute());
        // 恢复到第三次
        entity.recoveryFromMemento(mementoMananger.getMemento("third"));
        System.out.println("entity.getAttribute() = " + entity.getAttribute());
        // 恢复到第二次
        entity.recoveryFromMemento(mementoMananger.getMemento("second"));
        System.out.println("entity.getAttribute() = " + entity.getAttribute());
    }
}
