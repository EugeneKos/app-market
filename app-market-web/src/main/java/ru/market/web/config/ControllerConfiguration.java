package ru.market.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import ru.market.auth.api.AuthenticateService;

import ru.market.domain.service.IPersonService;

import ru.market.web.controller.MainController;
import ru.market.web.controller.rest.AuthenticateController;
import ru.market.web.controller.rest.MyController;
import ru.market.web.controller.rest.PersonController;

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
    public PersonController personController(IPersonService personService){
        return new PersonController(personService);
    }

    @Bean
    public AuthenticateController authenticateController(AuthenticateService authenticateService){
        return new AuthenticateController(authenticateService);
    }

    @Bean
    public MyController myController(){
        return new MyController();
    }
}
