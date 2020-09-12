package ru.market.cli.interactive.helper.command;

import java.util.HashMap;
import java.util.Map;

public class CommandArgumentWrapper {
    private Map<String, String> commandArguments = new HashMap<>();

    public void addCommandArgument(String commandArgumentKey, String commandArgument){
        commandArguments.put(commandArgumentKey, commandArgument);
    }

    public String getCommandArgument(String commandArgumentKey){
        return commandArguments.get(commandArgumentKey);
    }
}
