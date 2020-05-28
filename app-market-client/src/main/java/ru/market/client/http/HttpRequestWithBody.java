package ru.market.client.http;

public interface HttpRequestWithBody<RequestBody, ResponseBody> extends HttpRequest<ResponseBody> {
    RequestBody getRequestBody();
}
