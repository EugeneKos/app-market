package ru.market.client.http;

import ru.market.dto.error.ErrorDTO;

public interface HttpResponse<ResponseBody> {
    int getCode();
    String getMessage();
    ErrorDTO getError();
    ResponseBody getResponseBody();
}
