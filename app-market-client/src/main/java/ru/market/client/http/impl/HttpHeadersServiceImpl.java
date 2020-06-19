package ru.market.client.http.impl;

import ru.market.client.http.HttpHeader;
import ru.market.client.http.HttpHeadersService;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpHeadersServiceImpl implements HttpHeadersService {
    private Map<String, String> headers = new HashMap<>();

    @Override
    public void setHeaders(HttpURLConnection httpURLConnection) {
        for (HttpHeader httpHeader : HttpHeader.values()){
            String headerValue = headers.get(httpHeader.getRequestHeaderName());
            if(headerValue != null){
                httpURLConnection.setRequestProperty(httpHeader.getRequestHeaderName(), headerValue);
            }
        }
    }

    @Override
    public void saveHeaders(HttpURLConnection httpURLConnection) {
        for (HttpHeader httpHeader : HttpHeader.values()){
            String headerValue = httpURLConnection.getHeaderField(httpHeader.getResponseHeaderName());
            if(headerValue != null){
                headers.put(httpHeader.getRequestHeaderName(), headerValue);
            }
        }
    }

    @Override
    public void clearHeaders() {
        headers.clear();
    }
}
