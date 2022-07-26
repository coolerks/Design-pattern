package com.xiaoxu.principle.chain;

// 副校长审批
public class VicePrincipalHandler extends Handler {
    public VicePrincipalHandler(Handler next) {
        super(next);
    }

    @Override
    public void handle(Request request) {
        if (request.price <= 9000) {
            System.out.println("由副校长审批");
            return;
        }
        System.out.print("副校长交给下一个领导审批->");
        next.handle(request);
    }
}
