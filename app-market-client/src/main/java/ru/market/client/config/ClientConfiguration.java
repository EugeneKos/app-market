package ru.market.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.market.client.http.HttpConnection;
import ru.market.client.http.HttpHeadersService;
import ru.market.client.http.impl.HttpConnectionImpl;
import ru.market.client.http.impl.HttpHeadersServiceImpl;
import ru.market.client.rest.AuthenticateRestClient;
import ru.market.client.rest.CostLimitRestClient;
import ru.market.client.rest.CostRestClient;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.client.rest.OperationRestClient;
import ru.market.client.rest.PersonRestClient;
import ru.market.client.rest.UserRestClient;
import ru.market.client.rest.impl.AuthenticateRestClientImpl;
import ru.market.client.rest.impl.CostLimitRestClientImpl;
import ru.market.client.rest.impl.CostRestClientImpl;
import ru.market.client.rest.impl.MoneyAccountRestClientImpl;
import ru.market.client.rest.impl.OperationRestClientImpl;
import ru.market.client.rest.impl.PersonRestClientImpl;
import ru.market.client.rest.impl.UserRestClientImpl;
import ru.market.client.url.UrlProvider;

@Configuration
public class ClientConfiguration {
    @Bean
    public HttpHeadersService httpHeadersService(){
        return new HttpHeadersServiceImpl();
    }

    @Bean
    public HttpConnection httpConnection(HttpHeadersService httpHeadersService){
        return new HttpConnectionImpl(httpHeadersService);
    }

    @Bean
    public AuthenticateRestClient authenticateRestClient(HttpConnection httpConnection, UrlProvider urlProvider){
        return new AuthenticateRestClientImpl(httpConnection, urlProvider);
    }

    @Bean
    public UserRestClient userRestClient(HttpConnection httpConnection, UrlProvider urlProvider){
        return new UserRestClientImpl(httpConnection, urlProvider);
    }

    @Bean
    public PersonRestClient personRestClient(HttpConnection httpConnection, UrlProvider urlProvider){
        return new PersonRestClientImpl(httpConnection, urlProvider);
    }

    @Bean
    public MoneyAccountRestClient moneyAccountRestClient(HttpConnection httpConnection, UrlProvider urlProvider){
        return new MoneyAccountRestClientImpl(httpConnection, urlProvider);
    }

    @Bean
    public OperationRestClient operationRestClient(HttpConnection httpConnection, UrlProvider urlProvider){
        return new OperationRestClientImpl(httpConnection, urlProvider);
    }

    @Bean
    public CostLimitRestClient costLimitRestClient(HttpConnection httpConnection, UrlProvider urlProvider){
        return new CostLimitRestClientImpl(httpConnection, urlProvider);
    }

    @Bean
    public CostRestClient costRestClient(HttpConnection httpConnection, UrlProvider urlProvider){
        return new CostRestClientImpl(httpConnection, urlProvider);
    }
}
