package ru.market.cli.interactive.helper.command;

public class TypeWrapper<T> {
    private T typeValue;

    public T getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(T typeValue) {
        this.typeValue = typeValue;
    }
}
