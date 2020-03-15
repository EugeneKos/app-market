package ru.market.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.auth.api.AuthenticateService;
import ru.market.auth.impl.AuthFilterChainImpl;
import ru.market.auth.api.AuthFilterHandler;
import ru.market.auth.impl.AuthFilterHandlerImpl;
import ru.market.auth.impl.AuthenticateServiceImpl;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.data.session.api.SessionManagement;
import ru.market.data.session.impl.PersonDataManagementImpl;
import ru.market.data.session.impl.SessionManagementImpl;

import ru.market.domain.service.IPersonService;

@Configuration
public class AuthenticateConfiguration {
    @Bean
    public AuthFilterHandler authFilterHandler(){
        return new AuthFilterHandlerImpl();
    }

    @Bean
    public AuthenticateService authenticateService(IPersonService personService,
                                                   SessionManagement sessionManagement,
                                                   PersonDataManagement personDataManagement){

        return new AuthenticateServiceImpl(personService, sessionManagement, personDataManagement);
    }

    @Bean
    public SessionManagement sessionManagement(){
        return new SessionManagementImpl();
    }

    @Bean
    public PersonDataManagement personDataManagement(){
        return new PersonDataManagementImpl();
    }

    @Bean
    public AuthFilterChainImpl authFilterChain(AuthenticateService authenticateService, AuthFilterHandler authFilterHandler){
        return new AuthFilterChainImpl(authenticateService, authFilterHandler);
    }
}
