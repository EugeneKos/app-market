package ru.market.auth.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import ru.market.auth.api.Authenticate;
import ru.market.auth.api.AuthenticateService;

import ru.market.domain.service.IUserService;

import ru.market.data.session.api.SessionDataManager;
import ru.market.data.session.api.SessionManagement;
import ru.market.data.session.api.UserDataManager;
import ru.market.data.session.data.UserData;

import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;
import ru.market.dto.result.ResultStatus;
import ru.market.dto.user.UserSecretDTO;

import java.util.UUID;

public class AuthenticateServiceImpl implements AuthenticateService {
    private static final int INACTIVE_INTERVAL = 1200; // 20 min

    private IUserService userService;

    private PasswordEncoder passwordEncoder;

    private SessionManagement sessionManagement;
    private SessionDataManager sessionDataManager;
    private UserDataManager userDataManager;

    public AuthenticateServiceImpl(IUserService userService,
                                   PasswordEncoder passwordEncoder,
                                   SessionManagement sessionManagement,
                                   SessionDataManager sessionDataManager,
                                   UserDataManager userDataManager) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.sessionDataManager = sessionDataManager;
        this.sessionManagement = sessionManagement;
        this.userDataManager = userDataManager;
    }

    @Override
    public Authenticate authenticate(UsernamePasswordDTO usernamePasswordDTO) {
        UserSecretDTO secretDTO = userService.getByUsername(usernamePasswordDTO.getUsername());
        if(secretDTO == null){
            return failed("username not found");
        }

        return passwordEncoder.matches(usernamePasswordDTO.getPassword(), secretDTO.getPassword())
                ? authenticateSuccess(secretDTO)
                : failed("password doesn't match");
    }

    private Authenticate authenticateSuccess(UserSecretDTO secretDTO){
        UserData userData = new UserData();
        userData.setUserId(secretDTO.getId());
        userData.setPersonId(secretDTO.getPerson().getId());

        String secretKey = generateSecretKey();
        String authToken = passwordEncoder.encode(secretKey);

        sessionDataManager.setSecretKey(secretKey);
        userDataManager.setUserData(userData);

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
    public boolean isAuthenticate(String authToken) {
        if(StringUtils.isEmpty(authToken) || userDataManager.getUserData() == null){
            return false;
        }

        String secretKey = sessionDataManager.getSecretKey();
        if(StringUtils.isEmpty(secretKey)){
            return false;
        }

        return passwordEncoder.matches(secretKey, authToken);
    }

    @Override
    public void invalidate() {
        userDataManager.removeUserData();
        sessionManagement.invalidateSession();
    }
}
