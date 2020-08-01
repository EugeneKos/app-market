package ru.market.client.rest.impl;

import ru.market.client.exception.RestClientException;
import ru.market.client.exception.UnauthorizedException;
import ru.market.client.http.HttpResponse;
import ru.market.client.url.UrlProvider;
import ru.market.dto.error.ErrorDTO;

abstract class CommonRestClient {
    private UrlProvider urlProvider;

    CommonRestClient(UrlProvider urlProvider) {
        this.urlProvider = urlProvider;
    }

    String createUrl(String path){
        return String.format("%s://%s:%s/%s%s", urlProvider.schema(), urlProvider.ipAddress(),
                urlProvider.port(), urlProvider.root(), path
        );
    }

    <ResponseBody> void checkResponse(HttpResponse<ResponseBody> response) throws RestClientException {
        if(response.getCode() != 200){
            if(response.getCode() == 401){
                throw new UnauthorizedException("Unauthorized");
            }

            ErrorDTO error = response.getError();
            if(error != null){
                throw new RestClientException(String.format(
                        "Http status: [%s] message [%s] error [%s]",
                        response.getCode(), response.getMessage(), error.getError()
                ));
            } else {
                throw new RestClientException(String.format(
                        "Http status: [%s] message [%s]", response.getCode(), response.getMessage()
                ));
            }
        }
    }
}
