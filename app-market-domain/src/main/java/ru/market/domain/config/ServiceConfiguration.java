package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.converter.MoneyAccountConverter;
import ru.market.domain.converter.OperationConverter;
import ru.market.domain.converter.PersonConverter;
import ru.market.domain.converter.UserConverter;
import ru.market.domain.data.MoneyAccount;
import ru.market.domain.data.Person;
import ru.market.domain.repository.MoneyAccountRepository;
import ru.market.domain.repository.OperationRepository;
import ru.market.domain.repository.PersonRepository;
import ru.market.domain.repository.UserRepository;
import ru.market.domain.service.IMoneyAccountService;
import ru.market.domain.service.IOperationService;
import ru.market.domain.service.IPersonProvider;
import ru.market.domain.service.IPersonService;
import ru.market.domain.service.IUserService;
import ru.market.domain.service.OperationExecutor;
import ru.market.domain.service.impl.MoneyAccountServiceImpl;
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
                                    IPersonService personService,
                                    PersonConverter personConverter,
                                    PasswordEncoder passwordEncoder){

        UserServiceImpl userService = new UserServiceImpl(userRepository, userConverter, personService, personConverter);
        userService.setPasswordEncoder(passwordEncoder);
        return userService;
    }

    @Bean
    public IMoneyAccountService moneyAccountService(MoneyAccountRepository moneyAccountRepository,
                                                    MoneyAccountConverter moneyAccountConverter,
                                                    CommonValidator<MoneyAccount> validator,
                                                    IPersonProvider personProvider){

        return new MoneyAccountServiceImpl(moneyAccountRepository, moneyAccountConverter, validator, personProvider);
    }

    @Bean
    public IPersonProvider personProvider(SessionDataManager sessionDataManager, IPersonService personService){
        return new PersonProviderImpl(sessionDataManager, personService);
    }

    @Bean
    public OperationExecutor operationExecutor(OperationRepository operationRepository,
                                               OperationConverter operationConverter,
                                               IMoneyAccountService moneyAccountService){

        return new OperationExecutorImpl(operationRepository, operationConverter, moneyAccountService);
    }

    @Bean
    public IOperationService operationService(OperationRepository operationRepository,
                                              OperationConverter operationConverter,
                                              OperationExecutor operationExecutor){

        return new OperationServiceImpl(operationRepository, operationConverter, operationExecutor);
    }
}
