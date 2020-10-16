package ru.market.cli.interactive.starter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.market.cli.config.CLIConfiguration;
import ru.market.cli.interactive.element.Element;
import ru.market.cli.interactive.element.ElementStorage;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.printer.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InteractiveCLIStarter {
    public static void start(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CLIConfiguration.class);
        ElementStorage storage = applicationContext.getBean(ElementStorage.class);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Интерактивная командная утилита v1.0");

            String exitIdentification = IDElement.EXIT_CMD;
            String idElement = IDElement.MAIN_MENU;

            do {
                Element element = storage.getElementById(idElement);
                idElement = element.perform(reader);
            } while (!exitIdentification.equals(idElement));

            System.out.println("Завершение работы...");

        } catch (IOException e) {
            Printer.error("Ошибка во время выполнения!", InteractiveCLIStarter.class, e);
        }
    }
}
