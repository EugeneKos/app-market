package ru.market.client.url;

public interface UrlProvider {
    String schema();
    String ipAddress();
    String port();
    String root();
}
