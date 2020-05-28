package ru.market.client.rest.impl;

import ru.market.client.exception.RestClientException;
import ru.market.client.http.HttpResponse;

abstract class AbstractRestClient {
    private static final String SCHEMA = "http";
    private static final String IP_ADDRESS = "localhost";
    private static final String PORT = "8080";
    private static final String ROOT = "app-market-web";

    String createUrl(String path){
        return String.format("%s://%s:%s/%s%s", SCHEMA, IP_ADDRESS, PORT, ROOT, path);
    }

    <ResponseBody> void checkResponse(HttpResponse<ResponseBody> response) throws RestClientException {
        if(response.getCode() != 200){
            throw new RestClientException(String.format(
                    "Http status: %s message %s", response.getCode(), response.getMessage()
            ));
        }
    }
}
