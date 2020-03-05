package ru.market.domain.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.domain.converter.PersonConverter;

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
}
