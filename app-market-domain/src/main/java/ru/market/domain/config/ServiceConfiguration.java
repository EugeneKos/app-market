package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.domain.converter.CardConverter;
import ru.market.domain.converter.CashConverter;
import ru.market.domain.converter.PersonConverter;
import ru.market.domain.repository.CardRepository;
import ru.market.domain.repository.CashRepository;
import ru.market.domain.repository.PersonRepository;
import ru.market.domain.service.ICardService;
import ru.market.domain.service.ICashService;
import ru.market.domain.service.IPersonService;
import ru.market.domain.service.impl.CardServiceImpl;
import ru.market.domain.service.impl.CashServiceImpl;
import ru.market.domain.service.impl.PersonServiceImpl;

@Configuration
public class ServiceConfiguration {
    @Bean
    public IPersonService personService(PersonRepository personRepository, PersonConverter personConverter){
        return new PersonServiceImpl(personRepository, personConverter);
    }

    @Bean
    public ICardService cardService(CardRepository cardRepository, CardConverter cardConverter){
        return new CardServiceImpl(cardRepository, cardConverter);
    }

    @Bean
    public ICashService cashService(CashRepository cashRepository, CashConverter cashConverter){
        return new CashServiceImpl(cashRepository, cashConverter);
    }
}
