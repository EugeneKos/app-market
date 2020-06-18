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
    public AuthenticateRestClient authenticateRestClient(HttpConnection httpConnection){
        return new AuthenticateRestClientImpl(httpConnection);
    }

    @Bean
    public UserRestClient userRestClient(HttpConnection httpConnection){
        return new UserRestClientImpl(httpConnection);
    }

    @Bean
    public PersonRestClient personRestClient(HttpConnection httpConnection){
        return new PersonRestClientImpl(httpConnection);
    }

    @Bean
    public MoneyAccountRestClient moneyAccountRestClient(HttpConnection httpConnection){
        return new MoneyAccountRestClientImpl(httpConnection);
    }

    @Bean
    public OperationRestClient operationRestClient(HttpConnection httpConnection){
        return new OperationRestClientImpl(httpConnection);
    }

    @Bean
    public CostLimitRestClient costLimitRestClient(HttpConnection httpConnection){
        return new CostLimitRestClientImpl(httpConnection);
    }

    @Bean
    public CostRestClient costRestClient(HttpConnection httpConnection){
        return new CostRestClientImpl(httpConnection);
    }
}
