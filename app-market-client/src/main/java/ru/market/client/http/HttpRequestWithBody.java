package ru.market.client.http;

public interface HttpRequestWithBody<RequestBody> extends HttpRequest {
    RequestBody getRequestBody();
}
