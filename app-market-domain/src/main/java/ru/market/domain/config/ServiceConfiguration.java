package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.market.data.session.api.UserDataManager;
import ru.market.domain.converter.BankAccountConverter;
import ru.market.domain.converter.CardAccountConverter;
import ru.market.domain.converter.CashAccountConverter;
import ru.market.domain.converter.OperationConverter;
import ru.market.domain.converter.PersonConverter;
import ru.market.domain.converter.UserConverter;
import ru.market.domain.data.BankAccount;
import ru.market.domain.data.CardAccount;
import ru.market.domain.data.CashAccount;
import ru.market.domain.data.Operation;
import ru.market.domain.data.Person;
import ru.market.domain.data.User;
import ru.market.domain.repository.account.BankAccountRepository;
import ru.market.domain.repository.account.CardAccountRepository;
import ru.market.domain.repository.account.CashAccountRepository;
import ru.market.domain.repository.common.OperationRepository;
import ru.market.domain.repository.common.PersonRepository;
import ru.market.domain.repository.common.UserRepository;
import ru.market.domain.service.IBankAccountService;
import ru.market.domain.service.ICardAccountService;
import ru.market.domain.service.ICashAccountService;
import ru.market.domain.service.IOperationService;
import ru.market.domain.service.IPersonProvider;
import ru.market.domain.service.IPersonService;
import ru.market.domain.service.IUserService;
import ru.market.domain.service.OperationExecutor;
import ru.market.domain.service.impl.BankAccountServiceImpl;
import ru.market.domain.service.impl.CardAccountServiceImpl;
import ru.market.domain.service.impl.CashAccountServiceImpl;
import ru.market.domain.service.impl.OperationServiceImpl;
import ru.market.domain.service.impl.PersonProviderImpl;
import ru.market.domain.service.impl.PersonServiceImpl;
import ru.market.domain.service.impl.OperationExecutorImpl;
import ru.market.domain.service.impl.UserServiceImpl;
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
                                        CommonValidator<Person> validator){

        return new PersonServiceImpl(personRepository, personConverter, validator);
    }

    @Bean
    public IUserService userService(UserRepository userRepository,
                                    UserConverter userConverter,
                                    CommonValidator<User> validator,
                                    IPersonService personService,
                                    PersonConverter personConverter,
                                    PasswordEncoder passwordEncoder){

        UserServiceImpl userService = new UserServiceImpl(userRepository, userConverter, validator,
                personService, personConverter
        );
        userService.setPasswordEncoder(passwordEncoder);
        return userService;
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
    public IPersonProvider personProvider(UserDataManager userDataManager, IPersonService personService){
        return new PersonProviderImpl(userDataManager, personService);
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
