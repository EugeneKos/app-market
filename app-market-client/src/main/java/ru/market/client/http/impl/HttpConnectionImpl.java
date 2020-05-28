package ru.market.client.http.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.market.client.exception.HttpConnectionException;
import ru.market.client.http.HttpConnection;
import ru.market.client.http.HttpRequest;
import ru.market.client.http.HttpRequestWithBody;
import ru.market.client.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpConnectionImpl implements HttpConnection {
    private static final String AUTH_TOKEN_HEADER = "Auth-Token";
    private static final String SET_COOKIE_HEADER = "Set-Cookie";
    private static final String COOKIE_HEADER = "Cookie";

    private ObjectMapper mapper = new ObjectMapper();

    private String cookieHeader;
    private String authTokenHeader;

    @Override
    public <ResponseBody> HttpResponse<ResponseBody> get(HttpRequest<ResponseBody> request) throws HttpConnectionException {
        return sendRequest(request.getUrl(), request.getTypeReference(), urlConnection -> {});
    }

    @Override
    public <RequestBody, ResponseBody> HttpResponse<ResponseBody> put(HttpRequestWithBody<RequestBody, ResponseBody> request)
            throws HttpConnectionException {

        return sendRequest(request.getUrl(), request.getTypeReference(),
                new HttpURLConnectionWithBodyCustomizer<>(request.getRequestBody(), "PUT")
        );
    }

    @Override
    public <RequestBody, ResponseBody> HttpResponse<ResponseBody> post(HttpRequestWithBody<RequestBody, ResponseBody> request)
            throws HttpConnectionException {

        return sendRequest(request.getUrl(), request.getTypeReference(),
                new HttpURLConnectionWithBodyCustomizer<>(request.getRequestBody(), "POST")
        );
    }

    @Override
    public <ResponseBody> HttpResponse<ResponseBody> delete(HttpRequest<ResponseBody> request) throws HttpConnectionException {
        return sendRequest(request.getUrl(), request.getTypeReference(),
                urlConnection -> urlConnection.setRequestMethod("DELETE")
        );
    }

    private <ResponseBody> HttpResponse<ResponseBody> sendRequest(String urlStr, TypeReference<ResponseBody> typeReference,
                                                                  HttpURLConnectionCustomizer customizer)
            throws HttpConnectionException {

        try {
            URL url = new URL(urlStr);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Charset", "utf-8");

            if(cookieHeader != null){
                urlConnection.setRequestProperty(COOKIE_HEADER, cookieHeader);
            }
            if(authTokenHeader != null){
                urlConnection.setRequestProperty(AUTH_TOKEN_HEADER, authTokenHeader);
            }

            customizer.customize(urlConnection);

            int responseCode = urlConnection.getResponseCode();
            String responseMessage = urlConnection.getResponseMessage();

            HttpResponseImpl<ResponseBody> response = new HttpResponseImpl<>(responseCode, responseMessage);

            if (responseCode != 200) {
                cookieHeader = null;
                authTokenHeader = null;
                return response;
            }

            String cookie = urlConnection.getHeaderField(SET_COOKIE_HEADER);
            if(cookie != null){
                cookieHeader = cookie;
            }

            String authToken = urlConnection.getHeaderField(AUTH_TOKEN_HEADER);
            if(authToken != null){
                authTokenHeader = authToken;
            }

            InputStream inputStream = urlConnection.getInputStream();

            byte[] buffer = new byte[128];
            StringBuilder builder = new StringBuilder();

            while (inputStream.read(buffer) != -1) {
                builder.append(new String(createNewBuffer(buffer)));
            }

            String responseBodyJson = builder.toString();
            if(responseBodyJson.isEmpty()){
                return response;
            }

            fillResponseBody(responseBodyJson, typeReference, response);

            return response;

        } catch (IOException e) {
            throw new HttpConnectionException("Ошибка соединения", e);
        }
    }

    private byte[] createNewBuffer(byte[] buffer){
        byte[] newBuffer;

        List<Byte> byteList = new ArrayList<>();
        for (byte b : buffer){
            if(b != 0){
                byteList.add(b);
            }
        }
        newBuffer = new byte[byteList.size()];
        for (int i=0; i<byteList.size(); i++){
            newBuffer[i] = byteList.get(i);
        }

        return newBuffer;
    }

    private <ResponseBody> void fillResponseBody(String responseBodyJson, TypeReference<ResponseBody> typeReference,
                                                 HttpResponseImpl<ResponseBody> response) throws JsonProcessingException {

        ResponseBody responseBody = mapper.readValue(responseBodyJson, typeReference);

        response.setResponseBody(responseBody);
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
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod(method);
            urlConnection.setDoOutput(true);

            String requestBodyJson = mapper.writeValueAsString(requestBody);

            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(requestBodyJson.getBytes(StandardCharsets.UTF_8));
        }
    }
}
