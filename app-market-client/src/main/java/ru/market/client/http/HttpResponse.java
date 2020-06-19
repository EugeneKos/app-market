package ru.market.client.http;

public interface HttpResponse<ResponseBody> {
    int getCode();
    String getMessage();
    ResponseBody getResponseBody();
}
