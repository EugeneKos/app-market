package ru.market.client.http.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import ru.market.client.http.HttpRequestWithBody;

public class HttpRequestWithBodyImpl<RequestBody, ResponseBody> extends HttpRequestImpl<ResponseBody>
        implements HttpRequestWithBody<RequestBody, ResponseBody> {

    private RequestBody requestBody;

    public HttpRequestWithBodyImpl(String url, TypeReference<ResponseBody> typeReference, RequestBody requestBody) {
        super(url, typeReference);
        this.requestBody = requestBody;
    }

    @Override
    public RequestBody getRequestBody() {
        return requestBody;
    }
}
