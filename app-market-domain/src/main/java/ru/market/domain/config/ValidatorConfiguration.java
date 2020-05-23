package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Person;
import ru.market.domain.repository.MoneyAccountRepository;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.money.MoneyAccountValidator;
import ru.market.domain.validator.person.PersonValidator;

@Configuration
public class ValidatorConfiguration {
    @Bean
    public CommonValidator<Person> personValidator(){
        return new PersonValidator();
    }

    @Bean
    public CommonValidator<MoneyAccount> moneyAccountValidator(MoneyAccountRepository moneyAccountRepository){
        return new MoneyAccountValidator(moneyAccountRepository);
    }
}
