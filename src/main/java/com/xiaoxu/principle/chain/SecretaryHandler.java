package com.xiaoxu.principle.chain;

// 书记审批
public class SecretaryHandler extends Handler {
    public SecretaryHandler(Handler next) {
        super(next);
    }

    @Override
    public void handle(Request request) {
        if (request.price <= 5000) {
            System.out.println("由书记审批");
            return;
        }
        System.out.print("书记交给下一个领导审批->");
        next.handle(request);
    }
}
