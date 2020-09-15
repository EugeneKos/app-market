package ru.market.cli.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.market.client.config.ClientConfiguration;

@Configuration
@Import({ClientConfiguration.class, ManagerCLIConfiguration.class, UrlProviderConfiguration.class, InteractiveCLIConfiguration.class})
@ComponentScan(basePackages = {"ru.market.cli.printer"})
public class CLIConfiguration {
}
