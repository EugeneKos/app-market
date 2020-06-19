package ru.market.cli.printer;

import java.util.Collection;

public interface Printer {
    <T> void print(T object);
    <T> void print(Collection<T> objects);
    void print(Exception e);
}
