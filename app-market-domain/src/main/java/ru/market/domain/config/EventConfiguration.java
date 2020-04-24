package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.domain.event.AccountDeleteEventListener;
import ru.market.domain.event.PersonDeleteEventListener;

import ru.market.domain.service.IBankAccountService;
import ru.market.domain.service.IOperationService;

@Configuration
public class EventConfiguration {
    @Bean
    public PersonDeleteEventListener personDeleteEventListener(IBankAccountService bankAccountService){

        return new PersonDeleteEventListener(bankAccountService);
    }

    @Bean
    public AccountDeleteEventListener accountDeleteEventListener(IOperationService operationService){
        return new AccountDeleteEventListener(operationService);
    }
}
