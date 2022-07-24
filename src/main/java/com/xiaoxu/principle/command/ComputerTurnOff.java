package com.xiaoxu.principle.command;

public class ComputerTurnOff implements Command{
    private ComputerReceiver computerReceiver;

    public ComputerTurnOff(ComputerReceiver computerReceiver) {
        this.computerReceiver = computerReceiver;
    }

    @Override
    public void execute() {
        System.out.println("电脑-准备执行：");
        computerReceiver.off();
    }
}
