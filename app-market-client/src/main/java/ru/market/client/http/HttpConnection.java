package ru.market.client.http;

import ru.market.client.exception.HttpConnectionException;

public interface HttpConnection {
    <ResponseBody> HttpResponse<ResponseBody> get(HttpRequest<ResponseBody> request) throws HttpConnectionException;
    <RequestBody, ResponseBody> HttpResponse<ResponseBody> put(HttpRequestWithBody<RequestBody, ResponseBody> request) throws HttpConnectionException;
    <RequestBody, ResponseBody> HttpResponse<ResponseBody> post(HttpRequestWithBody<RequestBody, ResponseBody> request) throws HttpConnectionException;
    <ResponseBody> HttpResponse<ResponseBody> delete(HttpRequest<ResponseBody> request) throws HttpConnectionException;
}
