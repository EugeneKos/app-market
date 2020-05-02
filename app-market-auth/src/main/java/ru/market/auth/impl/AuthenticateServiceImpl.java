package ru.market.auth.impl;

import org.springframework.security.crypto.password.PasswordEncoder;

import ru.market.auth.api.Authenticate;
import ru.market.auth.api.AuthenticateService;

import ru.market.domain.service.IUserService;

import ru.market.data.session.api.SessionDataManager;
import ru.market.data.session.api.SessionManagement;
import ru.market.data.session.api.UserData;

import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;
import ru.market.dto.result.ResultStatus;
import ru.market.dto.user.UserAdditionalDTO;

import java.util.UUID;

public class AuthenticateServiceImpl implements AuthenticateService {
    private static final int INACTIVE_INTERVAL = 1200; // 20 min

    private IUserService userService;

    private PasswordEncoder passwordEncoder;

    private SessionManagement sessionManagement;
    private SessionDataManager sessionDataManager;

    public AuthenticateServiceImpl(IUserService userService,
                                   PasswordEncoder passwordEncoder,
                                   SessionManagement sessionManagement,
                                   SessionDataManager sessionDataManager) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.sessionDataManager = sessionDataManager;
        this.sessionManagement = sessionManagement;
    }

    @Override
    public Authenticate authenticate(UsernamePasswordDTO usernamePasswordDTO) {
        sessionDataManager.setUserData(new UserData());

        UserAdditionalDTO userAdditionalDTO = userService.getByUsername(usernamePasswordDTO.getUsername());
        if(userAdditionalDTO == null){
            return failed("username not found");
        }

        return passwordEncoder.matches(usernamePasswordDTO.getPassword(), userAdditionalDTO.getPassword())
                ? authenticateSuccess(userAdditionalDTO)
                : failed("password doesn't match");
    }

    private Authenticate authenticateSuccess(UserAdditionalDTO userAdditionalDTO){
        UserData userData = sessionDataManager.getUserData();

        String secretKey = generateSecretKey();
        String authToken = passwordEncoder.encode(secretKey);

        userData.setUserId(userAdditionalDTO.getId());
        userData.setPersonId(userAdditionalDTO.getPerson().getId());
        userData.setAuthenticate(true);
        userData.setSecretKey(secretKey);

        sessionManagement.setMaxInactiveInterval(INACTIVE_INTERVAL);
        return new Authenticate(authToken, new ResultDTO(ResultStatus.SUCCESS, "authenticate success"));
    }

    private Authenticate failed(String description){
        sessionManagement.invalidateSession();
        return new Authenticate(null, new ResultDTO(ResultStatus.FAILED, description));
    }

    private String generateSecretKey(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    @Override
    public boolean isAuthenticate() {
        return sessionDataManager.getUserData() != null && sessionDataManager.getUserData().isAuthenticate();
    }

    @Override
    public void invalidate() {
        sessionManagement.invalidateSession();
    }
}
