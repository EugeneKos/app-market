package ru.market.domain.config;

import org.dozer.DozerBeanMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.domain.converter.BankAccountConverter;
import ru.market.domain.converter.CardAccountConverter;
import ru.market.domain.converter.CashAccountConverter;
import ru.market.domain.converter.OperationConverter;
import ru.market.domain.converter.PersonConverter;
import ru.market.domain.converter.UserConverter;

@Configuration
public class ConverterConfiguration {
    @Bean
    public DozerBeanMapper dozerBeanMapper(){
        return new DozerBeanMapper();
    }

    @Bean
    public PersonConverter personConverter(DozerBeanMapper dozerBeanMapper){
        return new PersonConverter(dozerBeanMapper);
    }

    @Bean
    public UserConverter userConverter(DozerBeanMapper dozerBeanMapper){
        return new UserConverter(dozerBeanMapper);
    }

    @Bean
    public BankAccountConverter bankAccountConverter(DozerBeanMapper dozerBeanMapper){
        return new BankAccountConverter(dozerBeanMapper);
    }

    @Bean
    public CardAccountConverter cardAccountConverter(DozerBeanMapper dozerBeanMapper){
        return new CardAccountConverter(dozerBeanMapper);
    }

    @Bean
    public CashAccountConverter cashAccountConverter(DozerBeanMapper dozerBeanMapper){
        return new CashAccountConverter(dozerBeanMapper);
    }

    @Bean
    public OperationConverter operationConverter(DozerBeanMapper dozerBeanMapper){
        return new OperationConverter(dozerBeanMapper);
    }
}
