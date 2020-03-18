package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.dto.person.PersonDTO;

@Configuration
@Import(value = DomainConfiguration.class)
public class DomainTestConfiguration {
    @Bean
    @Primary
    public PersonDataManagement personDataManagement(){
        return new PersonDataManagement() {
            @Override
            public void setPerson(PersonDTO person) {

            }

            @Override
            public PersonDTO getPerson() {
                return null;
            }

            @Override
            public void removePerson() {

            }
        };
    }
}
