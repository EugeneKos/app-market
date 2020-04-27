package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.CardAccount;
import ru.market.domain.data.CashAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.Person;
import ru.market.domain.data.User;
import ru.market.domain.repository.account.CardAccountRepository;
import ru.market.domain.repository.account.CashAccountRepository;
import ru.market.domain.repository.common.UserRepository;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.validator.account.AccountValidator;
import ru.market.domain.validator.account.CardAccountValidator;
import ru.market.domain.validator.account.CashAccountValidator;
import ru.market.domain.validator.operation.OperationValidator;
import ru.market.domain.validator.person.PersonValidator;
import ru.market.domain.validator.user.UserValidator;

@Configuration
public class ValidatorConfiguration {
    @Bean
    public CommonValidator<Person> personValidator(){
        return new PersonValidator();
    }

    @Bean
    public CommonValidator<User> userValidator(UserRepository userRepository){
        return new UserValidator(userRepository);
    }

    @Bean
    public CommonValidator<BankAccount> bankAccountValidator(){
        return new AccountValidator<>();
    }

    @Bean
    public CommonValidator<CardAccount> cardAccountValidator(CardAccountRepository cardAccountRepository){
        return new CardAccountValidator(cardAccountRepository);
    }

    @Bean
    public CommonValidator<CashAccount> cashAccountValidator(CashAccountRepository cashAccountRepository){
        return new CashAccountValidator(cashAccountRepository);
    }

    @Bean
    public CommonValidator<Operation> operationValidator(){
        return new OperationValidator();
    }
}
