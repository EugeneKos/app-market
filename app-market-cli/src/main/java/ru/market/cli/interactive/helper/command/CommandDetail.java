package ru.market.cli.interactive.helper.command;

import java.util.function.BiConsumer;

public class CommandDetail<T> {
    private String cmdArgumentName;
    private boolean mandatory;
    private BiConsumer<T, String> objectConsumer;

    public CommandDetail(String cmdArgumentName,
                         boolean mandatory,
                         BiConsumer<T, String> objectConsumer) {

        this.cmdArgumentName = cmdArgumentName;
        this.mandatory = mandatory;
        this.objectConsumer = objectConsumer;
    }

    public String getCmdArgumentName() {
        return cmdArgumentName;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public BiConsumer<T, String> getObjectConsumer() {
        return objectConsumer;
    }
}
