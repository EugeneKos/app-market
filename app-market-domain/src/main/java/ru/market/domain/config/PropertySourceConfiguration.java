package ru.market.domain.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

@Configuration
public class PropertySourceConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertySourceConfiguration.class);

    private static final String SYSTEM_FILE_PROPERTY = "file";
    private static final String DEFAULT_FILE_NAME = "app-market.properties";

    @Bean
    public PropertyPlaceholderConfigurer placeholderConfigurer(){
        PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
        placeholderConfigurer.setLocation(getResource());
        return placeholderConfigurer;
    }

    private Resource getResource(){
        LOGGER.info("Инициализация Resource PropertyPlaceholderConfigurer");
        String systemFileProperty = System.getProperty(SYSTEM_FILE_PROPERTY);
        if(StringUtils.isEmpty(systemFileProperty)){
            LOGGER.warn("Системная переменная file не задана, инициализация ClassPathResource - file name = {}", DEFAULT_FILE_NAME);
            return new ClassPathResource(DEFAULT_FILE_NAME);
        }

        return new FileSystemResource(systemFileProperty);
    }
}
