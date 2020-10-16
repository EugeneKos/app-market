package ru.market.cli.interactive.command;

import ru.market.cli.interactive.element.Element;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.printer.Printer;
import ru.market.client.exception.RestClientException;
import ru.market.client.exception.UnauthorizedException;

import java.io.BufferedReader;

public abstract class InteractiveCommonCommand implements Element {
    public abstract String performCommand(BufferedReader reader);
    public abstract String getElementIdByException();
    public abstract String getElementIdByRestClientException();

    @Override
    public String perform(BufferedReader reader) {
        try {
            return performCommand(reader);
        } catch (UnauthorizedException e) {
            Printer.error(String.format("Ошибка авторизации пользователя. Подробнее: %s",
                    e.getMessage()), InteractiveCommonCommand.class, e);

            return IDElement.MAIN_MENU;
        } catch (RestClientException e){
            Printer.error(String.format("Ошибка во время выполнения команды. Подробнее: %s",
                    e.getMessage()), InteractiveCommonCommand.class, e);

            return getElementIdByRestClientException();
        } catch (Exception e){
            Printer.error(String.format("Ошибка во время выполнения команды. Подробнее: %s",
                    e.getMessage()), InteractiveCommonCommand.class, e);

            return getElementIdByException();
        }
    }
}
