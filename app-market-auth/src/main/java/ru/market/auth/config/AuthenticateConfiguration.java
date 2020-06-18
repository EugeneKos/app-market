package ru.market.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.market.auth.api.AuthFilterChain;
import ru.market.auth.api.AuthenticateService;
import ru.market.auth.filter.AuthTokenFilter;
import ru.market.auth.filter.AuthenticateFilter;
import ru.market.auth.filter.CostLimitRequestFilter;
import ru.market.auth.filter.CostRequestFilter;
import ru.market.auth.filter.MoneyAccountRequestFilter;
import ru.market.auth.filter.OperationRequestFilter;
import ru.market.auth.filter.PersonRequestFilter;
import ru.market.auth.impl.AuthFilterChainImpl;
import ru.market.auth.api.AuthFilterHandler;
import ru.market.auth.impl.AuthFilterHandlerImpl;
import ru.market.auth.impl.AuthenticateServiceImpl;

import ru.market.data.session.api.SessionDataManager;
import ru.market.data.session.api.SessionManagement;

import ru.market.domain.service.ICostLimitService;
import ru.market.domain.service.ICostService;
import ru.market.domain.service.IMoneyAccountService;
import ru.market.domain.service.IUserService;

import java.util.Arrays;

@Configuration
public class AuthenticateConfiguration {
    @Bean
    public AuthFilterHandler authFilterHandler(){
        return new AuthFilterHandlerImpl();
    }

    @Bean
    public AuthenticateService authenticateService(IUserService userService,
                                                   PasswordEncoder passwordEncoder,
                                                   SessionManagement sessionManagement,
                                                   SessionDataManager sessionDataManager){

        return new AuthenticateServiceImpl(userService, passwordEncoder, sessionManagement, sessionDataManager);
    }

    @Bean
    @Scope("prototype")
    public AuthFilterChain authFilterChain(AuthFilterHandler authFilterHandler,
                                           AuthenticateService authenticateService,
                                           SessionDataManager sessionDataManager,
                                           PasswordEncoder passwordEncoder,
                                           IMoneyAccountService moneyAccountService,
                                           ICostLimitService costLimitService,
                                           ICostService costService){

        AuthFilterChainImpl authFilterChain = new AuthFilterChainImpl(authFilterHandler);

        authFilterChain.registerFilters(Arrays.asList(
                new AuthenticateFilter(authenticateService),
                new AuthTokenFilter(sessionDataManager, passwordEncoder),
                new PersonRequestFilter(sessionDataManager),
                new MoneyAccountRequestFilter(sessionDataManager, moneyAccountService),
                new OperationRequestFilter(sessionDataManager, moneyAccountService),
                new CostLimitRequestFilter(sessionDataManager, costLimitService),
                new CostRequestFilter(sessionDataManager, costLimitService, costService, moneyAccountService)
        ));

        return authFilterChain;
    }
}
