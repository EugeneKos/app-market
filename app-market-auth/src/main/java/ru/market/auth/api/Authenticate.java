package ru.market.auth.api;

import ru.market.dto.result.ResultDTO;

public class Authenticate {
    private String authToken;
    private ResultDTO resultDTO;

    public Authenticate(String authToken, ResultDTO resultDTO) {
        this.authToken = authToken;
        this.resultDTO = resultDTO;
    }

    public String getAuthToken() {
        return authToken;
    }

    public ResultDTO getResultDTO() {
        return resultDTO;
    }
}
