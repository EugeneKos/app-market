package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import ru.market.data.session.api.UserDataManager;
import ru.market.data.session.data.UserData;
import ru.market.domain.service.IPersonProvider;

@Configuration
@Import(value = DomainConfiguration.class)
public class DomainTestConfiguration {
    @Bean
    @Primary
    public UserDataManager userDataManager(){
        return new UserDataManager() {
            @Override
            public void setUserData(UserData userData) {

            }

            @Override
            public UserData getUserData() {
                return new UserData();
            }

            @Override
            public void removeUserData() {

            }
        };
    }

    @Bean
    @Primary
    public IPersonProvider personProvider(){
        return () -> null;
    }
}
