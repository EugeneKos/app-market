package ru.market.client.http.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import ru.market.client.http.HttpRequest;

public class HttpRequestImpl<ResponseBody> implements HttpRequest<ResponseBody> {
    private String url;
    private TypeReference<ResponseBody> responseBodyClass;

    public HttpRequestImpl(String url, TypeReference<ResponseBody> responseBodyClass) {
        this.url = url;
        this.responseBodyClass = responseBodyClass;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public TypeReference<ResponseBody> getTypeReference() {
        return responseBodyClass;
    }
}
