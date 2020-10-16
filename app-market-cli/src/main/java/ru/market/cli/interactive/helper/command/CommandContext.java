package ru.market.cli.interactive.helper.command;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandContext {
    private Map<CommandArgument, Object> commandArgumentMap = new HashMap<>();

    public <T> void putCommandArgument(CommandArgument key, T value){
        commandArgumentMap.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getCommandArgument(CommandArgument key){
        Object value = commandArgumentMap.get(key);

        if(value == null){
            throw new IllegalStateException(String.format("Попытка получить пустое значение аргумента: %s", key));
        }

        return (T) value;
    }


    public enum CommandArgument {
        MONEY_ACCOUNT_ID, USER_ID, COST_LIMIT_ID, COST_ID
    }
}
