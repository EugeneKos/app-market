package ru.market.data.session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.data.session.api.RequestBodyManagement;
import ru.market.data.session.api.SessionDataManager;
import ru.market.data.session.api.SessionManagement;
import ru.market.data.session.api.UserDataManager;
import ru.market.data.session.impl.RequestBodyManagementImpl;
import ru.market.data.session.impl.SessionDataManagerImpl;
import ru.market.data.session.impl.SessionManagementImpl;
import ru.market.data.session.impl.UserDataManagerImpl;

@Configuration
public class DataSessionConfiguration {
    @Bean
    public SessionManagement sessionManagement(){
        return new SessionManagementImpl();
    }

    @Bean
    public SessionDataManager sessionDataManager(){
        return new SessionDataManagerImpl();
    }

    @Bean
    public UserDataManager userDataManager(){
        return new UserDataManagerImpl();
    }

    @Bean
    public RequestBodyManagement requestBodyManagement(){
        return new RequestBodyManagementImpl();
    }
}
