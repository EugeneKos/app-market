package ru.market.cli.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.ed.microlib.context.CliManagerApplicationContext;

import ru.market.cli.printer.Printer;
import ru.market.cli.printer.impl.JSONPrinter;

@Configuration
@Import({CommandConfiguration.class, CliManagerApplicationContext.class})
public class CLIConfiguration {
    @Bean
    public Printer printer(){
        return new JSONPrinter();
    }
}
