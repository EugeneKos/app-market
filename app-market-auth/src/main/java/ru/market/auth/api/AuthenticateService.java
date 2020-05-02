package ru.market.auth.api;

import ru.market.dto.auth.UsernamePasswordDTO;

public interface AuthenticateService {
    Authenticate authenticate(UsernamePasswordDTO usernamePasswordDTO);
    boolean isAuthenticate();
    void invalidate();
}
