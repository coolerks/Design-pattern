package com.xiaoxu.principle.command;

public class TvTurnOn implements Command{
    private TvReceiver tvReceiver;

    public TvTurnOn(TvReceiver tvReceiver) {
        this.tvReceiver = tvReceiver;
    }

    @Override
    public void execute() {
        System.out.println("电视-准备执行：");
        tvReceiver.on();
    }
}
