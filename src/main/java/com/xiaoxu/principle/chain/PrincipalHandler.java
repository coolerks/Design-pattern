package com.xiaoxu.principle.chain;

// 校长审批
public class PrincipalHandler extends Handler {
    public PrincipalHandler(Handler next) {
        super(next);
    }

    @Override
    public void handle(Request request) {
        System.out.println("校长审批");
    }
}
