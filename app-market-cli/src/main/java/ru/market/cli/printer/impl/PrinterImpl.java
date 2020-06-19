package ru.market.cli.printer.impl;

import ru.market.cli.printer.Printer;

import java.util.Collection;

public class PrinterImpl implements Printer {
    @Override
    public <T> void print(T object) {
        System.out.println(object);
    }

    @Override
    public <T> void print(Collection<T> objects) {
        for (T object : objects){
            print(object);
        }
    }

    @Override
    public void print(Exception e) {
        System.out.println("Exception: " + e.getMessage());
    }
}
