package ru.market.client.http.impl;

import ru.market.client.http.HttpRequest;

public class HttpRequestImpl implements HttpRequest {
    private String url;

    public HttpRequestImpl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
