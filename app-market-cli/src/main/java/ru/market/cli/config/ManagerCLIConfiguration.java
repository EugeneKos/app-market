package ru.market.cli.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.ed.microlib.context.CliManagerApplicationContext;

@Configuration
@Import({CliManagerApplicationContext.class})
@ComponentScan(basePackages = {"ru.market.cli.manager.command"})
public class ManagerCLIConfiguration {
}
