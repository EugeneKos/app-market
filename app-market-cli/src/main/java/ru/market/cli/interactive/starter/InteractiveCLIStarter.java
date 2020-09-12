package ru.market.cli.interactive.starter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.market.cli.config.CLIConfiguration;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.interactive.element.impl.ExitCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InteractiveCLIStarter {
    public static void start(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CLIConfiguration.class);
        Menu mainMenu = applicationContext.getBean("mainMenu", Menu.class);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Интерактивная командная утилита v1.0");
            mainMenu.perform(reader, new ExitCommand());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
