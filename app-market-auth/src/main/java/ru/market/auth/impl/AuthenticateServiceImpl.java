package ru.market.auth.impl;

import org.springframework.security.crypto.password.PasswordEncoder;

import ru.market.auth.api.AuthenticateService;

import ru.market.domain.service.IUserService;

import ru.market.data.session.api.SessionManagement;
import ru.market.data.session.api.UserDataManager;
import ru.market.data.session.data.UserData;

import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;
import ru.market.dto.result.ResultStatus;
import ru.market.dto.user.UserSecretDTO;

public class AuthenticateServiceImpl implements AuthenticateService {
    private static final int INACTIVE_INTERVAL = 1200; // 20 min

    private IUserService userService;

    private PasswordEncoder passwordEncoder;

    private SessionManagement sessionManagement;
    private UserDataManager userDataManager;

    public AuthenticateServiceImpl(IUserService userService,
                                   PasswordEncoder passwordEncoder,
                                   SessionManagement sessionManagement,
                                   UserDataManager userDataManager) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.sessionManagement = sessionManagement;
        this.userDataManager = userDataManager;
    }

    @Override
    public ResultDTO authenticate(UsernamePasswordDTO usernamePasswordDTO) {
        UserSecretDTO secretDTO = userService.getByUsername(usernamePasswordDTO.getUsername());
        if(secretDTO == null){
            return new ResultDTO(ResultStatus.FAILED, "username not found");
        }

        return passwordEncoder.matches(usernamePasswordDTO.getPassword(), secretDTO.getPassword())
                ? authenticateSuccess(secretDTO)
                : new ResultDTO(ResultStatus.FAILED, "password doesn't match");
    }

    private ResultDTO authenticateSuccess(UserSecretDTO secretDTO){
        UserData userData = new UserData();
        userData.setUserId(secretDTO.getId());
        userData.setPersonId(secretDTO.getPerson().getId());

        userDataManager.setUserData(userData);

        sessionManagement.setMaxInactiveInterval(INACTIVE_INTERVAL);
        return new ResultDTO(ResultStatus.SUCCESS, "authenticate success");
    }

    @Override
    public boolean isAuthenticate() {
        return userDataManager.getUserData() != null;
    }

    @Override
    public void invalidate() {
        userDataManager.removeUserData();
        sessionManagement.invalidateSession();
    }
}
