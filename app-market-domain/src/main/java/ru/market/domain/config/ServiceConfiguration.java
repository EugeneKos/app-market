package ru.market.domain.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.domain.converter.BankAccountConverter;
import ru.market.domain.converter.CardAccountConverter;
import ru.market.domain.converter.CashAccountConverter;
import ru.market.domain.converter.PersonConverter;
import ru.market.domain.repository.BankAccountRepository;
import ru.market.domain.repository.CardAccountRepository;
import ru.market.domain.repository.CashAccountRepository;
import ru.market.domain.repository.PersonRepository;
import ru.market.domain.service.IBankAccountService;
import ru.market.domain.service.ICardAccountService;
import ru.market.domain.service.ICashAccountService;
import ru.market.domain.service.IPersonProvider;
import ru.market.domain.service.IPersonService;
import ru.market.domain.service.impl.BankAccountServiceImpl;
import ru.market.domain.service.impl.CardAccountServiceImpl;
import ru.market.domain.service.impl.CashAccountServiceImpl;
import ru.market.domain.service.impl.PersonProviderImpl;
import ru.market.domain.service.impl.PersonServiceImpl;

@Configuration
public class ServiceConfiguration {
    @Bean
    public IPersonService personService(PersonRepository personRepository,
                                        PersonConverter personConverter,
                                        ApplicationEventPublisher eventPublisher){

        PersonServiceImpl personService = new PersonServiceImpl(personRepository, personConverter);
        personService.setEventPublisher(eventPublisher);
        return personService;
    }

    @Bean
    public IBankAccountService bankAccountService(BankAccountRepository bankAccountRepository,
                                                  BankAccountConverter bankAccountConverter,
                                                  IPersonProvider personProvider){

        return new BankAccountServiceImpl(bankAccountRepository, bankAccountConverter, personProvider);
    }

    @Bean
    public ICardAccountService cardAccountService(CardAccountRepository cardAccountRepository,
                                                  CardAccountConverter cardAccountConverter,
                                                  IPersonProvider personProvider){

        return new CardAccountServiceImpl(cardAccountRepository, cardAccountConverter, personProvider);
    }

    @Bean
    public ICashAccountService cashAccountService(CashAccountRepository cashAccountRepository,
                                                  CashAccountConverter cashAccountConverter,
                                                  IPersonProvider personProvider){

        return new CashAccountServiceImpl(cashAccountRepository, cashAccountConverter, personProvider);
    }

    @Bean
    public IPersonProvider personProvider(PersonDataManagement personDataManagement, PersonConverter personConverter){
        return new PersonProviderImpl(personDataManagement, personConverter);
    }
}
