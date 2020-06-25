package ru.market.cli.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.cli.utils.PropertyLoader;
import ru.market.client.url.UrlProvider;

@Configuration
public class UrlProviderConfiguration {
    @Bean
    public UrlProvider urlProvider(){
        return new UrlProviderImpl();
    }

    public class UrlProviderImpl implements UrlProvider {
        @Override
        public String schema() {
            return getProperty("app-market-cli@url.schema", "http");
        }

        @Override
        public String ipAddress() {
            return getProperty("app-market-cli@url.ipAddress", "localhost");
        }

        @Override
        public String port() {
            return getProperty("app-market-cli@url.port", "8080");
        }

        @Override
        public String root() {
            return getProperty("app-market-cli@url.root", "app-market-web");
        }

        private String getProperty(String name, String defaultValue){
            String schema = PropertyLoader.getProperty(name);
            return (schema == null || schema.length() == 0) ? defaultValue : schema;
        }
    }
}
