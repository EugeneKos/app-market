package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import ru.market.domain.service.IPersonProvider;

@Configuration
@Import(value = DomainConfiguration.class)
public class DomainTestConfiguration {
    @Bean
    @Primary
    public IPersonProvider personProvider(){
        return () -> null;
    }
}
