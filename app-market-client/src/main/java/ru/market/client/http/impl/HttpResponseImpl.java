package ru.market.client.http.impl;

import ru.market.client.http.HttpResponse;

public class HttpResponseImpl<ResponseBody> implements HttpResponse<ResponseBody> {
    private int code;
    private String message;
    private ResponseBody responseBody;

    HttpResponseImpl(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ResponseBody getResponseBody() {
        return responseBody;
    }

    void setResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }
}
