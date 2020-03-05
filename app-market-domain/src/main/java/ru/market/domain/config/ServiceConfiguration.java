package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.domain.converter.PersonConverter;
import ru.market.domain.repository.PersonRepository;
import ru.market.domain.service.IPersonService;
import ru.market.domain.service.impl.PersonServiceImpl;

@Configuration
public class ServiceConfiguration {
    @Bean
    public IPersonService personService(PersonRepository personRepository, PersonConverter personConverter){
        return new PersonServiceImpl(personRepository, personConverter);
    }
}
