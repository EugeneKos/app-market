package ru.market.domain.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.market.domain.converter.UserConverter;
import ru.market.domain.repository.UserRepository;
import ru.market.domain.service.IUserService;
import ru.market.domain.service.impl.UserServiceImpl;

@Configuration
@Import({DataConfiguration.class})
public class DomainSpringConfiguration {
    @Bean
    public DozerBeanMapper dozerBeanMapper(){
        return new DozerBeanMapper();
    }

    @Bean
    public UserConverter userConverter(DozerBeanMapper dozerBeanMapper){
        return new UserConverter(dozerBeanMapper);
    }

    @Bean
    public IUserService userService(UserRepository userRepository, UserConverter userConverter){
        return new UserServiceImpl(userRepository, userConverter);
    }
}
