package com.xiaoxu.principle.chain;

// 院长审批
public class DeanHandler extends Handler {
    public DeanHandler(Handler next) {
        super(next);
    }

    @Override
    public void handle(Request request) {
        if (request.price <= 7000) {
            System.out.println("由院长审批");
            return;
        }
        System.out.print("院长交给下一个领导审批->");
        next.handle(request);
    }
}
