package ru.market.client.http;

import java.net.HttpURLConnection;

public interface HttpHeadersService {
    void setHeaders(HttpURLConnection httpURLConnection);
    void saveHeaders(HttpURLConnection httpURLConnection);
    void clearHeaders();
}
