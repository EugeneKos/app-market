package ru.market.client.http.impl;

import ru.market.client.http.HttpRequestWithBody;

public class HttpRequestWithBodyImpl<RequestBody> extends HttpRequestImpl
        implements HttpRequestWithBody<RequestBody> {

    private RequestBody requestBody;

    public HttpRequestWithBodyImpl(String url, RequestBody requestBody) {
        super(url);
        this.requestBody = requestBody;
    }

    @Override
    public RequestBody getRequestBody() {
        return requestBody;
    }
}
