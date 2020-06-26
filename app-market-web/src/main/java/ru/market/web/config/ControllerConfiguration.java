package ru.market.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import ru.market.data.session.api.SessionDataManager;

import ru.market.domain.service.ICostLimitService;
import ru.market.domain.service.ICostService;
import ru.market.domain.service.IMoneyAccountService;
import ru.market.domain.service.IOperationService;
import ru.market.domain.service.IPersonService;
import ru.market.domain.service.IUserService;

import ru.market.auth.api.AuthenticateService;

import ru.market.web.controller.MainController;
import ru.market.web.controller.rest.AuthenticateController;
import ru.market.web.controller.rest.CostController;
import ru.market.web.controller.rest.CostLimitController;
import ru.market.web.controller.rest.MoneyAccountController;
import ru.market.web.controller.rest.HealthCheckController;
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
                                             SessionDataManager sessionDataManager){

        return new PersonController(personService, sessionDataManager);
    }

    @Bean
    public UserController userController(IUserService userService,
                                         SessionDataManager sessionDataManager,
                                         AuthenticateService authenticateService){

        return new UserController(userService, sessionDataManager, authenticateService);
    }

    @Bean
    public AuthenticateController authenticateController(AuthenticateService authenticateService){
        return new AuthenticateController(authenticateService);
    }

    @Bean
    public HealthCheckController healthCheckController(){
        return new HealthCheckController();
    }

    @Bean
    public MoneyAccountController moneyAccountController(IMoneyAccountService moneyAccountService){
        return new MoneyAccountController(moneyAccountService);
    }

    @Bean
    public OperationController operationController(IOperationService operationService,
                                                   SessionDataManager sessionDataManager){

        return new OperationController(operationService, sessionDataManager);
    }

    @Bean
    public CostLimitController costLimitController(ICostLimitService costLimitService){
        return new CostLimitController(costLimitService);
    }

    @Bean
    public CostController costController(ICostService costService, SessionDataManager sessionDataManager){
        return new CostController(costService, sessionDataManager);
    }
}
