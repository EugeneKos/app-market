package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.domain.event.AccountDeleteEventListener;
import ru.market.domain.event.PersonDeleteEventListener;

import ru.market.domain.service.ICardAccountService;
import ru.market.domain.service.ICashAccountService;
import ru.market.domain.service.IOperationService;

@Configuration
public class EventConfiguration {
    @Bean
    public PersonDeleteEventListener personDeleteEventListener(ICardAccountService cardAccountService,
                                                               ICashAccountService cashAccountService){

        return new PersonDeleteEventListener(cardAccountService, cashAccountService);
    }

    @Bean
    public AccountDeleteEventListener accountDeleteEventListener(IOperationService operationService){
        return new AccountDeleteEventListener(operationService);
    }
}
