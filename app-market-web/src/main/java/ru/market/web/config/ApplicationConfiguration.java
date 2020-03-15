package ru.market.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.market.auth.config.AuthenticateConfiguration;

import ru.market.domain.config.DomainConfiguration;

@Configuration
@Import({DomainConfiguration.class, ControllerConfiguration.class, AuthenticateConfiguration.class})
public class ApplicationConfiguration {
}
