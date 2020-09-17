package ru.market.cli.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.cli.utils.PropertyLoader;
import ru.market.client.http.ConnectionParamsProvider;
import ru.market.client.url.UrlProvider;

@Configuration
public class ClientConnectionConfiguration {
    @Bean
    public UrlProvider urlProvider(){
        return new UrlProviderImpl();
    }

    @Bean
    public ConnectionParamsProvider connectionParamsProvider(){
        return new ConnectionParamsProviderImpl();
    }

    public static class UrlProviderImpl implements UrlProvider {
        @Override
        public String schema() {
            return PropertyLoader.getProperty("app-market-cli@url.schema", "http");
        }

        @Override
        public String ipAddress() {
            return PropertyLoader.getProperty("app-market-cli@url.ipAddress", "localhost");
        }

        @Override
        public String port() {
            return PropertyLoader.getProperty("app-market-cli@url.port", "8080");
        }

        @Override
        public String root() {
            return PropertyLoader.getProperty("app-market-cli@url.root", "app-market-web");
        }
    }

    public static class ConnectionParamsProviderImpl implements ConnectionParamsProvider {
        @Override
        public int timeout() {
            return Integer.parseInt(PropertyLoader.getProperty(
                    "app-market-cli@connection.timeout", "15000"
            ));
        }
    }
}
