package ru.market.web.auth;

import ru.market.dto.auth.AuthAnswerDTO;
import ru.market.dto.auth.UsernamePasswordDTO;

public interface AuthenticateService {
    AuthAnswerDTO authenticate(UsernamePasswordDTO usernamePasswordDTO);
    void invalidate();
}
