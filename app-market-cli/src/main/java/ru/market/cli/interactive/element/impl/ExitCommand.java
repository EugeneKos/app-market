package ru.market.cli.interactive.element.impl;

import ru.market.cli.interactive.element.Menu;

import java.io.BufferedReader;

public class ExitCommand implements Menu {
    @Override
    public String name() {
        return "exit";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public void back(BufferedReader reader) {
        System.out.println("Завершение работы...");
    }
}
