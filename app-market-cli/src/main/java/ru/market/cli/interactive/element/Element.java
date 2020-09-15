package ru.market.cli.interactive.element;

import java.io.BufferedReader;

public interface Element {
    String name();
    void perform(BufferedReader reader, Menu menu);
}
