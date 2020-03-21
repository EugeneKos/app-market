package ru.market.domain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DataConfiguration.class, ServiceConfiguration.class, ConverterConfiguration.class, EventConfiguration.class})
public class DomainConfiguration {
}
