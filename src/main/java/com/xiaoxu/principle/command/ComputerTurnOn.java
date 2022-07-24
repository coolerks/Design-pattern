package com.xiaoxu.principle.command;

public class ComputerTurnOn implements Command{
    private ComputerReceiver computerReceiver;

    public ComputerTurnOn(ComputerReceiver computerReceiver) {
        this.computerReceiver = computerReceiver;
    }

    @Override
    public void execute() {
        System.out.println("电脑-准备执行：");
        computerReceiver.on();
    }
}
