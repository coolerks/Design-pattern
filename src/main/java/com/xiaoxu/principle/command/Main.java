package com.xiaoxu.principle.command;

public class Main {
    public static void main(String[] args) {
        // 实例化一个调用者
        Invoker invoker = new Invoker();
        // 实例电脑开机命令
        ComputerReceiver computerReceiver = new ComputerReceiver();
        Command computerTurnOn = new ComputerTurnOn(computerReceiver);
        // 实例化电视开机命令
        TvReceiver tvReceiver = new TvReceiver();
        Command tvTurnOn = new TvTurnOn(tvReceiver);
        // 添加命令
        invoker.addCommands(computerTurnOn);
        invoker.addCommands(tvTurnOn);
        // 执行命令
        invoker.executeCommand();

        // 实例化电脑关机、电视关机命令
        Command computerTurnOff = new ComputerTurnOff(computerReceiver);
        Command tvTurnOff = new TvTurnOff(tvReceiver);
        // 清空命令
        invoker.clearCommands();
        // 添加命令
        invoker.addCommands(computerTurnOff);
        invoker.addCommands(tvTurnOff);
        // 执行命令
        invoker.executeCommand();
    }
}
