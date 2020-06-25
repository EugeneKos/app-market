package ru.market.cli;

import ru.ed.microlib.CliManagerApp;

import ru.market.cli.config.CLIConfiguration;

public class AppMarketCLI {
    public static void main(String[] args) {
        CliManagerApp.cliStart(CLIConfiguration.class, "app-market-cli-1.0");
    }
}
