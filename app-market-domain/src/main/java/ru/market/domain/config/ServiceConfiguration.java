package ru.market.domain.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.domain.converter.BankAccountConverter;
import ru.market.domain.converter.CardAccountConverter;
import ru.market.domain.converter.CashAccountConverter;
import ru.market.domain.converter.OperationConverter;
import ru.market.domain.converter.PersonConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.data.CardAccount;
import ru.market.domain.data.CashAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.Person;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.account.CardAccountRepository;
import ru.market.domain.repository.account.CashAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.repository.common.PersonRepository;
import ru.market.domain.service.IBankAccountService;
import ru.market.domain.service.ICardAccountService;
import ru.market.domain.service.ICashAccountService;
import ru.market.domain.service.IOperationService;
import ru.market.domain.service.IPersonProvider;
import ru.market.domain.service.IPersonService;
import ru.market.domain.service.OperationExecutor;
import ru.market.domain.service.impl.BankAccountServiceImpl;
import ru.market.domain.service.impl.CardAccountServiceImpl;
import ru.market.domain.service.impl.CashAccountServiceImpl;
import ru.market.domain.service.impl.OperationServiceImpl;
import ru.market.domain.service.impl.PersonProviderImpl;
import ru.market.domain.service.impl.PersonServiceImpl;
import ru.market.domain.service.impl.OperationExecutorImpl;
import ru.market.domain.validator.CommonValidator;

@Configuration
public class ServiceConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IPersonService personService(PersonRepository personRepository,
                                        PersonConverter personConverter,
                                        CommonValidator<Person> validator,
                                        ApplicationEventPublisher eventPublisher,
                                        PasswordEncoder passwordEncoder){

        PersonServiceImpl personService = new PersonServiceImpl(personRepository, personConverter, validator);
        personService.setEventPublisher(eventPublisher);
        personService.setPasswordEncoder(passwordEncoder);
        return personService;
    }

    @Bean
    public IBankAccountService bankAccountService(BankAccountRepository bankAccountRepository,
                                                  BankAccountConverter bankAccountConverter,
                                                  CommonValidator<BankAccount> validator,
                                                  IPersonProvider personProvider){

        return new BankAccountServiceImpl(bankAccountRepository, bankAccountConverter, validator, personProvider);
    }

    @Bean
    public ICardAccountService cardAccountService(CardAccountRepository cardAccountRepository,
                                                  CardAccountConverter cardAccountConverter,
                                                  CommonValidator<CardAccount> validator,
                                                  IPersonProvider personProvider){

        return new CardAccountServiceImpl(cardAccountRepository, cardAccountConverter, validator, personProvider);
    }

    @Bean
    public ICashAccountService cashAccountService(CashAccountRepository cashAccountRepository,
                                                  CashAccountConverter cashAccountConverter,
                                                  CommonValidator<CashAccount> validator,
                                                  IPersonProvider personProvider){

        return new CashAccountServiceImpl(cashAccountRepository, cashAccountConverter, validator, personProvider);
    }

    @Bean
    public IPersonProvider personProvider(PersonDataManagement personDataManagement, PersonConverter personConverter){
        return new PersonProviderImpl(personDataManagement, personConverter);
    }

    @Bean
    public OperationExecutor operationExecutor(OperationRepository operationRepository,
                                               OperationConverter operationConverter,
                                               IBankAccountService bankAccountService,
                                               CommonValidator<Operation> validator){

        return new OperationExecutorImpl(operationRepository, operationConverter, bankAccountService, validator);
    }

    @Bean
    public IOperationService operationService(OperationRepository operationRepository,
                                              OperationConverter operationConverter,
                                              OperationExecutor operationExecutor){

        return new OperationServiceImpl(operationRepository, operationConverter, operationExecutor);
    }
}
