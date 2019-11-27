package ru.market.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.market.domain.converter.UserConverter;
import ru.market.domain.repository.UserRepository;
import ru.market.domain.service.IUserService;
import ru.market.domain.service.impl.UserServiceImpl;

@Configuration
@Import({DomainDataConfiguration.class, DomainConverterConfiguration.class})
public class DomainServiceConfiguration {
    @Bean
    public IUserService userService(UserRepository userRepository, UserConverter userConverter){
        return new UserServiceImpl(userRepository, userConverter);
    }
}
