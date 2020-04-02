package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.domain.event.PersonDeleteEventListener;

import ru.market.domain.service.ICardAccountService;
import ru.market.domain.service.ICashAccountService;

@Configuration
public class EventConfiguration {
    @Bean
    public PersonDeleteEventListener personDeleteEventListener(ICardAccountService cardAccountService,
                                                               ICashAccountService cashAccountService){

        return new PersonDeleteEventListener(cardAccountService, cashAccountService);
    }
}
