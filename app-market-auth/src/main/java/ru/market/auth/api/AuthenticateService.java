package ru.market.auth.api;

import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;

public interface AuthenticateService {
    ResultDTO authenticate(UsernamePasswordDTO usernamePasswordDTO);
    boolean isAuthenticate();
    void invalidate();
}
