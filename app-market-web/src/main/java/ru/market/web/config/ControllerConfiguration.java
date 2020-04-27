package ru.market.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import ru.market.domain.service.ICardAccountService;
import ru.market.domain.service.ICashAccountService;
import ru.market.domain.service.IOperationService;
import ru.market.domain.service.IPersonService;
import ru.market.domain.service.IUserService;

import ru.market.auth.api.AuthenticateService;

import ru.market.data.session.api.UserDataManager;
import ru.market.data.session.api.RequestBodyManagement;

import ru.market.web.controller.MainController;
import ru.market.web.controller.rest.CardAccountController;
import ru.market.web.controller.rest.CashAccountController;
import ru.market.web.controller.rest.AuthenticateController;
import ru.market.web.controller.rest.MyController;
import ru.market.web.controller.rest.OperationController;
import ru.market.web.controller.rest.PersonController;
import ru.market.web.controller.rest.UserController;

@Configuration
@EnableWebMvc
public class ControllerConfiguration {
    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public MainController mainController(){
        return new MainController();
    }

    @Bean
    public PersonController personController(IPersonService personService,
                                             RequestBodyManagement requestBodyManagement,
                                             UserDataManager userDataManager){

        return new PersonController(personService, requestBodyManagement, userDataManager);
    }

    @Bean
    public UserController userController(IUserService userService,
                                         UserDataManager userDataManager,
                                         AuthenticateService authenticateService){

        return new UserController(userService, userDataManager, authenticateService);
    }

    @Bean
    public AuthenticateController authenticateController(AuthenticateService authenticateService){
        return new AuthenticateController(authenticateService);
    }

    @Bean
    public MyController myController(){
        return new MyController();
    }

    @Bean
    public CardAccountController cardController(ICardAccountService cardAccountService){
        return new CardAccountController(cardAccountService);
    }

    @Bean
    public CashAccountController cashController(ICashAccountService cashAccountService){
        return new CashAccountController(cashAccountService);
    }

    @Bean
    public OperationController operationController(IOperationService operationService,
                                                   RequestBodyManagement requestBodyManagement){

        return new OperationController(operationService, requestBodyManagement);
    }
}
