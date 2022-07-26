package com.xiaoxu.principle.chain;

// 主任审批
public class DirectorHandler extends Handler {
    public DirectorHandler(Handler next) {
        super(next);
    }

    @Override
    public void handle(Request request) {
        if (request.price <= 3000) {
            System.out.println("由主任审批");
            return;
        }
        System.out.print("主任交给下一个领导审批->");
        next.handle(request);
    }
}
