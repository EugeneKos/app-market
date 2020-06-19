package ru.market.client.http;

import com.fasterxml.jackson.core.type.TypeReference;

public interface HttpRequest<ResponseBody> {
    String getUrl();
    TypeReference<ResponseBody> getTypeReference();
}
