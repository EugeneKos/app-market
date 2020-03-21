package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.domain.event.PersonDeleteEventListener;

import ru.market.domain.service.ICardService;
import ru.market.domain.service.ICashService;

@Configuration
public class EventConfiguration {
    @Bean
    public PersonDeleteEventListener personDeleteEventListener(ICardService cardService, ICashService cashService){
        return new PersonDeleteEventListener(cardService, cashService);
    }
}
