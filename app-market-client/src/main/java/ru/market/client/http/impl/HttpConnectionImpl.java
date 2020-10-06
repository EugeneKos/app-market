package ru.market.client.http.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.client.exception.HttpConnectionException;
import ru.market.client.exception.JsonMapperException;
import ru.market.client.http.ConnectionParamsProvider;
import ru.market.client.http.HttpConnection;
import ru.market.client.http.HttpHeadersService;
import ru.market.client.http.HttpRequest;
import ru.market.client.http.HttpRequestWithBody;
import ru.market.client.http.HttpResponse;
import ru.market.dto.error.ErrorDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpConnectionImpl implements HttpConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionImpl.class);

    private HttpHeadersService httpHeadersService;
    private ConnectionParamsProvider connectionParamsProvider;

    private ObjectMapper mapper = new ObjectMapper();

    public HttpConnectionImpl(HttpHeadersService httpHeadersService, ConnectionParamsProvider connectionParamsProvider) {
        this.httpHeadersService = httpHeadersService;
        this.connectionParamsProvider = connectionParamsProvider;
    }

    @Override
    public <ResponseBody> HttpResponse<ResponseBody> get(HttpRequest request, TypeReference<ResponseBody> typeReference)
            throws HttpConnectionException {

        return sendRequest(request.getUrl(), typeReference, urlConnection -> {});
    }

    @Override
    public <RequestBody, ResponseBody> HttpResponse<ResponseBody> put(
            HttpRequestWithBody<RequestBody> request, TypeReference<ResponseBody> typeReference
    ) throws HttpConnectionException {

        return sendRequest(request.getUrl(), typeReference,
                new HttpURLConnectionWithBodyCustomizer<>(request.getRequestBody(), "PUT")
        );
    }

    @Override
    public <RequestBody, ResponseBody> HttpResponse<ResponseBody> post(
            HttpRequestWithBody<RequestBody> request, TypeReference<ResponseBody> typeReference
    ) throws HttpConnectionException {

        return sendRequest(request.getUrl(), typeReference,
                new HttpURLConnectionWithBodyCustomizer<>(request.getRequestBody(), "POST")
        );
    }

    @Override
    public <ResponseBody> HttpResponse<ResponseBody> delete(HttpRequest request, TypeReference<ResponseBody> typeReference)
            throws HttpConnectionException {

        return sendRequest(request.getUrl(), typeReference,
                urlConnection -> urlConnection.setRequestMethod("DELETE")
        );
    }

    private <ResponseBody> HttpResponse<ResponseBody> sendRequest(String urlStr, TypeReference<ResponseBody> typeReference,
                                                                  HttpURLConnectionCustomizer customizer)
            throws HttpConnectionException {

        try {
            URL url = new URL(urlStr);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(connectionParamsProvider.timeout());
            setRequestProperty(urlConnection, RequestProperty.ACCEPT_CHARSET_UTF_8);

            httpHeadersService.setHeaders(urlConnection);

            customizer.customize(urlConnection);

            int responseCode = urlConnection.getResponseCode();
            String responseMessage = urlConnection.getResponseMessage();

            HttpResponseImpl<ResponseBody> response = new HttpResponseImpl<>(responseCode, responseMessage);

            if (responseCode != 200) {
                response.setError(getResponseBody(urlConnection.getErrorStream(), new TypeReference<ErrorDTO>() {}));
                return response;
            }

            httpHeadersService.saveHeaders(urlConnection);

            response.setResponseBody(getResponseBody(urlConnection.getInputStream(), typeReference));

            return response;

        } catch (IOException e) {
            throw new HttpConnectionException("Ошибка отправки/полуения запроса/ответа", e);
        }
    }

    private <ResponseBody> ResponseBody getResponseBody(
            InputStream inputStream, TypeReference<ResponseBody> typeReference) throws IOException {

        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
        }

        String responseBodyStr = builder.toString();
        LOGGER.info("Response body = {}", responseBodyStr);

        if(responseBodyStr.isEmpty() || isHtmlPage(responseBodyStr)){
            return null;
        }

        try {
            return mapper.readValue(responseBodyStr, typeReference);
        } catch (JsonProcessingException e) {
            throw new JsonMapperException("Ошибка преобразования Json", e);
        }
    }

    private boolean isHtmlPage(String body){
        String htmlPagePattern = "^<html>.+</html>$";
        return body.matches(htmlPagePattern);
    }

    private void setRequestProperty(HttpURLConnection urlConnection, RequestProperty requestProperty){
        urlConnection.setRequestProperty(requestProperty.getName(), requestProperty.getValue());
    }

    interface HttpURLConnectionCustomizer {
        void customize(HttpURLConnection urlConnection) throws IOException;
    }

    class HttpURLConnectionWithBodyCustomizer<RequestBody> implements HttpURLConnectionCustomizer {
        private RequestBody requestBody;
        private String method;

        HttpURLConnectionWithBodyCustomizer(RequestBody requestBody, String method) {
            this.requestBody = requestBody;
            this.method = method;
        }

        @Override
        public void customize(HttpURLConnection urlConnection) throws IOException {
            setRequestProperty(urlConnection, RequestProperty.CONTENT_TYPE_JSON);
            urlConnection.setRequestMethod(method);
            urlConnection.setDoOutput(true);

            String requestBodyJson = mapper.writeValueAsString(requestBody);

            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(requestBodyJson.getBytes(StandardCharsets.UTF_8));
        }
    }

    private enum RequestProperty {
        ACCEPT_CHARSET_UTF_8("Accept-Charset", "utf-8"),
        CONTENT_TYPE_JSON("Content-Type", "application/json");

        private String name;
        private String value;

        RequestProperty(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }
}
