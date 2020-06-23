package ru.market.cli.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.ed.microlib.context.CliManagerApplicationContext;

import ru.market.client.config.ClientConfiguration;

@Configuration
@Import({ClientConfiguration.class, CliManagerApplicationContext.class, UrlProviderConfiguration.class})
@ComponentScan(basePackages = {"ru.market.cli.command"})
public class CLIConfiguration {
}
