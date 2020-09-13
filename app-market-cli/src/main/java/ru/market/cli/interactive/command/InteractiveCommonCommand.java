package ru.market.cli.interactive.command;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class InteractiveCommonCommand implements Command {
    public abstract void perform(BufferedReader reader);

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        try {
            perform(reader);
        } catch (Exception e){
            System.out.println(String.format("Ошибка во время выполнения команды. Подробнее: %s", e));
        }

        try {
            System.out.print("Для продолжения введите что-нибудь или просто нажмите Enter: ");
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        menu.back(reader);
    }
}
