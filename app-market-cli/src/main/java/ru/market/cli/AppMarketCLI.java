package ru.market.cli;

import ru.ed.microlib.CliManagerApp;

import ru.market.cli.config.CLIConfiguration;
import ru.market.cli.interactive.starter.InteractiveCLIStarter;

public class AppMarketCLI {
    private static final String MANAGER = "manager";
    private static final String INTERACTIVE = "interactive";

    public static void main(String[] args) {
        if(args == null || args.length != 1){
            System.err.println("Требуется задать аргумент: (manager/interactive)");
            return;
        }

        if(MANAGER.equals(args[0])){
            CliManagerApp.cliStart(CLIConfiguration.class, "app-market-cli-1.0");
        } else if(INTERACTIVE.equals(args[0])){
            InteractiveCLIStarter.start();
        } else {
            System.err.println("Задан неверный аргумент [" + args[0] + "]. Требуется: (manager/interactive)");
        }
    }
}
