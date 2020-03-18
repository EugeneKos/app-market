package ru.market.data.session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.data.session.api.RequestBodyManagement;
import ru.market.data.session.api.SessionManagement;
import ru.market.data.session.impl.PersonDataManagementImpl;
import ru.market.data.session.impl.RequestBodyManagementImpl;
import ru.market.data.session.impl.SessionManagementImpl;

@Configuration
public class DataSessionConfiguration {
    @Bean
    public SessionManagement sessionManagement(){
        return new SessionManagementImpl();
    }

    @Bean
    public PersonDataManagement personDataManagement(){
        return new PersonDataManagementImpl();
    }

    @Bean
    public RequestBodyManagement requestBodyManagement(){
        return new RequestBodyManagementImpl();
    }
}
