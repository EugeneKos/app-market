package ru.market.client.http;

import com.fasterxml.jackson.core.type.TypeReference;

import ru.market.client.exception.HttpConnectionException;

public interface HttpConnection {
    <ResponseBody> HttpResponse<ResponseBody> get(HttpRequest request, TypeReference<ResponseBody> typeReference)
            throws HttpConnectionException;

    <RequestBody, ResponseBody> HttpResponse<ResponseBody> put(
            HttpRequestWithBody<RequestBody> request, TypeReference<ResponseBody> typeReference
    ) throws HttpConnectionException;

    <RequestBody, ResponseBody> HttpResponse<ResponseBody> post(
            HttpRequestWithBody<RequestBody> request, TypeReference<ResponseBody> typeReference
    ) throws HttpConnectionException;

    <ResponseBody> HttpResponse<ResponseBody> delete(HttpRequest request, TypeReference<ResponseBody> typeReference)
            throws HttpConnectionException;
}
