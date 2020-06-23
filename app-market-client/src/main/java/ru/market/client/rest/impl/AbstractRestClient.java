package ru.market.client.rest.impl;

import ru.market.client.exception.RestClientException;
import ru.market.client.http.HttpResponse;
import ru.market.client.url.UrlProvider;

abstract class AbstractRestClient {
    private UrlProvider urlProvider;

    AbstractRestClient(UrlProvider urlProvider) {
        this.urlProvider = urlProvider;
    }

    String createUrl(String path){
        return String.format("%s://%s:%s/%s%s", urlProvider.schema(), urlProvider.ipAddress(),
                urlProvider.port(), urlProvider.root(), path
        );
    }

    <ResponseBody> void checkResponse(HttpResponse<ResponseBody> response) throws RestClientException {
        if(response.getCode() != 200){
            throw new RestClientException(String.format(
                    "Http status: %s message %s", response.getCode(), response.getMessage()
            ));
        }
    }
}
