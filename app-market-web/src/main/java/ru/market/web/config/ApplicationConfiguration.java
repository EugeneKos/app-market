package ru.market.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.market.domain.config.DomainConfiguration;
import ru.market.domain.service.IPersonService;
import ru.market.web.auth.AuthenticateService;
import ru.market.web.auth.AuthenticateServiceImpl;

@Configuration
@Import({DomainConfiguration.class, ControllerConfiguration.class})
public class ApplicationConfiguration {
    @Bean
    public AuthenticateService authenticateService(IPersonService personService){
        return new AuthenticateServiceImpl(personService);
    }
}
