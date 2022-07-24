package com.xiaoxu.principle.command;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private List<Command> commands = new ArrayList<>();

    public void addCommands(Command command) {
        commands.add(command);
    }

    public void clearCommands() {
        commands.clear();
    }

    public void executeCommand() {
        commands.forEach(Command::execute);
    }
}
