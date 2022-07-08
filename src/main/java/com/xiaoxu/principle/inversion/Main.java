package com.xiaoxu.principle.inversion;

/**
 * 依赖倒置原则
 */
public class Main {
    public static void main(String[] args) {
        Persion persion = new Persion();
        persion.receive(new Email());
        persion.receive(new Wechat());
    }
}

class Email implements IReceiver {
    @Override
    public String getMessage() {
        return "电子邮件内容";
    }
}

class Wechat implements IReceiver {
    @Override
    public String getMessage() {
        return "微信消息内容";
    }
}


interface IReceiver {
    String getMessage();
}

class Persion {
    public void receive(IReceiver receiver) {
        System.out.println(receiver.getMessage());
    }
}
