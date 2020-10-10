package ru.market.cli.interactive.element;

import java.io.BufferedReader;

public interface Element {
    /**
     * @return id данного элемента
     */
    String id();

    /**
     * Выполняет какое-либо действие
     * @param reader Для получения данных введенных пользователем с консоли
     * @return id следующего элемента для вызова
     */
    String perform(BufferedReader reader);
}
