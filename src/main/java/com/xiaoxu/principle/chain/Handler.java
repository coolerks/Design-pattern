package com.xiaoxu.principle.chain;

public abstract class Handler {
    protected Handler next;

    public abstract void handle(Request request);

    public Handler(Handler next) {
        this.next = next;
    }
}
