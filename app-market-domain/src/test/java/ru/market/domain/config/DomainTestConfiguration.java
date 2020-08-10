package ru.market.domain.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import ru.market.domain.data.Person;
import ru.market.domain.service.IPersonProvider;
import ru.market.domain.service.IPersonService;
import ru.market.domain.service.IUserService;
import ru.market.dto.person.PersonDTO;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;

import javax.annotation.PostConstruct;

@Configuration
@Import({
        DataConfiguration.class, ServiceConfiguration.class, ConverterConfiguration.class,
        ValidatorConfiguration.class
})
public class DomainTestConfiguration {
    @Autowired
    private IUserService userService;

    @Autowired
    private IPersonService personService;

    private PersonDTO personDTO;

    @PostConstruct
    public void initialize(){
        RegistrationDTO registrationDTO = RegistrationDTO.builder()
                .firstName("FirstNameTest")
                .lastName("LastNameTest")
                .middleName("MiddleNameTest")
                .username("usernameTest")
                .password("password123Test")
                .build();

        UserDTO userDTO = userService.registration(registrationDTO);

        personDTO = userDTO.getPerson();
    }

    @Bean
    @Primary
    public IPersonProvider personProvider(){
        return new IPersonProvider() {
            @Override
            public Person getCurrentPerson() {
                return personService.getPersonById(personDTO.getId());
            }

            @Override
            public Long getCurrentPersonId() {
                return personDTO.getId();
            }
        };
    }
}
