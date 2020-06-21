package ru.market.cli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.ed.microlib.context.CliManagerApplicationContext;

@Configuration
@Import({CommandConfiguration.class, CliManagerApplicationContext.class})
public class CLIConfiguration {
}
