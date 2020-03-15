package ru.market.auth.api;

import ru.market.dto.auth.AuthAnswerDTO;
import ru.market.dto.auth.UsernamePasswordDTO;

public interface AuthenticateService {
    AuthAnswerDTO authenticate(UsernamePasswordDTO usernamePasswordDTO);
    boolean isAuthenticate();
    void invalidate();
}
