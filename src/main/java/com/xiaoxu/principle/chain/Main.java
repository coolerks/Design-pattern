package com.xiaoxu.principle.chain;

// 职责链模式
public class Main {
    public static void main(String[] args) {
        Request request = new Request("空调", 3331);

        Handler handler = new DirectorHandler(new SecretaryHandler(new DeanHandler(new VicePrincipalHandler(new PrincipalHandler(null)))));
        handler.handle(request);
    }
}
