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
import ru.market.dto.user.UserStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class AuthenticateServiceImpl implements AuthenticateService {
    private static final int INACTIVE_INTERVAL = 1200; // 20 min
    private static final int ALLOWED_PASSWORD_ATTEMPTS = 3;
    private static final Duration LOCK_TIME = Duration.ofMinutes(15); // 15 min

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
        initializeUserData();

        UserAdditionalDTO userAdditionalDTO = userService.getByUsername(usernamePasswordDTO.getUsername());
        if(userAdditionalDTO == null){
            return failed("Пользователь не найден");
        }

        UserStatus status = userAdditionalDTO.getStatus();
        if(status == UserStatus.TEMPORARY_LOCK){
            LocalDateTime timestampStatus = userAdditionalDTO.getTimestampStatus();
            Duration duration = Duration.between(timestampStatus, LocalDateTime.now());

            int compare = duration.compareTo(LOCK_TIME);
            if(compare < 0){
                Long remainingLockTimeSeconds = LOCK_TIME.getSeconds() - duration.getSeconds();
                LocalTime remainingLockTime = LocalTime.ofSecondOfDay(remainingLockTimeSeconds);

                return failed(String.format(
                        "Пользователь временно заблокирован. Осталось %d мин. %d сек.",
                        remainingLockTime.getMinute(),
                        remainingLockTime.getSecond()
                ));
            }

            userService.updateUserStatusByUsername(
                    userAdditionalDTO.getUsername(), ru.market.domain.data.enumeration.UserStatus.ACTIVE
            );
        }

        return passwordEncoder.matches(usernamePasswordDTO.getPassword(), userAdditionalDTO.getPassword())
                ? authenticateSuccess(userAdditionalDTO)
                : failedAuthenticate(userAdditionalDTO);
    }

    private void initializeUserData(){
        if(sessionDataManager.getUserData() == null){
            sessionDataManager.setUserData(new UserData());
        }
    }

    private Authenticate authenticateSuccess(UserAdditionalDTO userAdditionalDTO){
        UserData userData = sessionDataManager.getUserData();

        String secretKey = generateSecretKey();
        String authToken = passwordEncoder.encode(secretKey);

        userData.setUserId(userAdditionalDTO.getId());
        userData.setPersonId(userAdditionalDTO.getPerson().getId());
        userData.setAuthenticate(true);
        userData.setSecretKey(secretKey);

        userService.updatePasswordAttemptCountByUsername(userAdditionalDTO.getUsername(), 0);

        sessionManagement.setMaxInactiveInterval(INACTIVE_INTERVAL);
        return new Authenticate(authToken, new ResultDTO(ResultStatus.SUCCESS, "authenticate success"));
    }

    private Authenticate failedAuthenticate(UserAdditionalDTO userAdditionalDTO){
        UserData userData = sessionDataManager.getUserData();

        if(userData.isAuthenticate()){
            sessionManagement.invalidateSession();
        }

        int passwordAttemptCount = userAdditionalDTO.getPasswordAttemptCount() + 1;

        if(passwordAttemptCount == ALLOWED_PASSWORD_ATTEMPTS){
            userService.updateUserStatusByUsername(
                    userAdditionalDTO.getUsername(), ru.market.domain.data.enumeration.UserStatus.TEMPORARY_LOCK
            );

            userService.updatePasswordAttemptCountByUsername(userAdditionalDTO.getUsername(), 0);
            return failed("Превышено число попыток ввода пароля. Установлена временная блокировка");
        }

        userService.updatePasswordAttemptCountByUsername(userAdditionalDTO.getUsername(), passwordAttemptCount);

        return failed(String.format(
                "Пароль не совпадает. Осталось попыток (%d)", ALLOWED_PASSWORD_ATTEMPTS - passwordAttemptCount
        ));
    }

    private Authenticate failed(String description){
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
