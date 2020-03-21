package ru.market.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ru.market.auth.api.AuthFilterChain;
import ru.market.auth.api.AuthenticateService;
import ru.market.auth.filter.AuthenticateFilter;
import ru.market.auth.filter.CardRequestFilter;
import ru.market.auth.filter.CashRequestFilter;
import ru.market.auth.filter.PersonRequestFilter;
import ru.market.auth.impl.AuthFilterChainImpl;
import ru.market.auth.api.AuthFilterHandler;
import ru.market.auth.impl.AuthFilterHandlerImpl;
import ru.market.auth.impl.AuthenticateServiceImpl;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.data.session.api.RequestBodyManagement;
import ru.market.data.session.api.SessionManagement;

import ru.market.domain.service.ICardService;
import ru.market.domain.service.ICashService;
import ru.market.domain.service.IPersonService;

import java.util.Arrays;

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
    @Scope("prototype")
    public AuthFilterChain authFilterChain(AuthFilterHandler authFilterHandler,
                                           AuthenticateService authenticateService,
                                           PersonDataManagement personDataManagement,
                                           RequestBodyManagement requestBodyManagement,
                                           ICardService cardService,
                                           ICashService cashService){

        AuthFilterChainImpl authFilterChain = new AuthFilterChainImpl(authFilterHandler);

        authFilterChain.registerFilters(Arrays.asList(
                new AuthenticateFilter(authenticateService),
                new PersonRequestFilter(personDataManagement, requestBodyManagement),
                new CardRequestFilter(personDataManagement, cardService),
                new CashRequestFilter(personDataManagement, cashService)
        ));

        return authFilterChain;
    }
}
