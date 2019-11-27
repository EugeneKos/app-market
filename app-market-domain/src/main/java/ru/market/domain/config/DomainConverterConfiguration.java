package ru.market.domain.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.domain.converter.UserConverter;

@Configuration
public class DomainConverterConfiguration {
    @Bean
    public DozerBeanMapper dozerBeanMapper(){
        return new DozerBeanMapper();
    }

    @Bean
    public UserConverter userConverter(DozerBeanMapper dozerBeanMapper){
        return new UserConverter(dozerBeanMapper);
    }
}
