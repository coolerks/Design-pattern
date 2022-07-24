package com.xiaoxu.principle.command;

public class TvTurnOff implements Command{
    private TvReceiver tvReceiver;

    public TvTurnOff(TvReceiver tvReceiver) {
        this.tvReceiver = tvReceiver;
    }

    @Override
    public void execute() {
        System.out.println("电视-准备执行：");
        tvReceiver.off();
    }
}
