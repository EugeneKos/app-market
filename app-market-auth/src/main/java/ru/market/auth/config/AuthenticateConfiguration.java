package ru.market.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.market.auth.api.AuthFilterChain;
import ru.market.auth.api.AuthenticateService;
import ru.market.auth.filter.AuthenticateFilter;
import ru.market.auth.filter.CardAccountRequestFilter;
import ru.market.auth.filter.CashAccountRequestFilter;
import ru.market.auth.filter.OperationRequestFilter;
import ru.market.auth.filter.PersonRequestFilter;
import ru.market.auth.impl.AuthFilterChainImpl;
import ru.market.auth.api.AuthFilterHandler;
import ru.market.auth.impl.AuthFilterHandlerImpl;
import ru.market.auth.impl.AuthenticateServiceImpl;

import ru.market.data.session.api.RequestBodyManagement;
import ru.market.data.session.api.SessionManagement;
import ru.market.data.session.api.UserDataManager;

import ru.market.domain.service.IBankAccountService;
import ru.market.domain.service.ICardAccountService;
import ru.market.domain.service.ICashAccountService;
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
                                                   UserDataManager userDataManager){

        return new AuthenticateServiceImpl(userService, passwordEncoder, sessionManagement, userDataManager);
    }

    @Bean
    @Scope("prototype")
    public AuthFilterChain authFilterChain(AuthFilterHandler authFilterHandler,
                                           AuthenticateService authenticateService,
                                           UserDataManager userDataManage,
                                           RequestBodyManagement requestBodyManagement,
                                           ICardAccountService cardAccountService,
                                           ICashAccountService cashAccountService,
                                           IBankAccountService bankAccountService){

        AuthFilterChainImpl authFilterChain = new AuthFilterChainImpl(authFilterHandler);

        authFilterChain.registerFilters(Arrays.asList(
                new AuthenticateFilter(authenticateService),
                new PersonRequestFilter(userDataManage, requestBodyManagement),
                new CardAccountRequestFilter(userDataManage, cardAccountService),
                new CashAccountRequestFilter(userDataManage, cashAccountService),
                new OperationRequestFilter(userDataManage, requestBodyManagement, bankAccountService)
        ));

        return authFilterChain;
    }
}
