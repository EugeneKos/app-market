package ru.market.web;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import ru.market.domain.config.DomainSpringConfiguration;
import ru.market.web.controller.ControllerConfiguration;
import ru.market.web.security.SecurityConfiguration;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                DomainSpringConfiguration.class,
                SecurityConfiguration.class,
                ControllerConfiguration.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
