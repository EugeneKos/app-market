package ru.market.client.http.impl;

import ru.market.client.http.HttpResponse;

import ru.market.dto.error.ErrorDTO;

public class HttpResponseImpl<ResponseBody> implements HttpResponse<ResponseBody> {
    private int code;
    private String message;
    private ErrorDTO error;
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

    @Override
    public ErrorDTO getError() {
        return error;
    }

    void setError(ErrorDTO error) {
        this.error = error;
    }
}
