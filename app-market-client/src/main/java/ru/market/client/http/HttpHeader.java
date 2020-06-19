package ru.market.client.http;

public enum HttpHeader {
    COOKIE("Cookie", "Set-Cookie"),
    AUTH_TOKEN("Auth-Token", "Auth-Token");

    private String requestHeaderName;
    private String responseHeaderName;

    HttpHeader(String requestHeaderName, String responseHeaderName) {
        this.requestHeaderName = requestHeaderName;
        this.responseHeaderName = responseHeaderName;
    }

    public String getRequestHeaderName() {
        return requestHeaderName;
    }

    public String getResponseHeaderName() {
        return responseHeaderName;
    }
}
