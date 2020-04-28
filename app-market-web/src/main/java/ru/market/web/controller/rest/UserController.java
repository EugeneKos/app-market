package ru.market.web.controller.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.auth.api.AuthenticateService;
import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.service.IUserService;
import ru.market.dto.result.ResultDTO;
import ru.market.dto.user.RegistrationDTO;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserPasswordDTO;
import ru.market.dto.user.UserUsernameDTO;

@RestController
public class UserController {
    private IUserService userService;
    private SessionDataManager sessionDataManager;
    private AuthenticateService authenticateService;

    public UserController(IUserService userService,
                          SessionDataManager sessionDataManager,
                          AuthenticateService authenticateService) {

        this.userService = userService;
        this.sessionDataManager = sessionDataManager;
        this.authenticateService = authenticateService;
    }

    @RequestMapping(path = "/registration", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public UserDTO registration(@RequestBody RegistrationDTO registrationDTO){
        return userService.registration(registrationDTO);
    }

    @RequestMapping(path = "/change/username", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public UserDTO changeUsername(@RequestBody UserUsernameDTO usernameDTO){
        Long userId = sessionDataManager.getUserData().getUserId();
        UserDTO userDTO = userService.changeUsername(usernameDTO, userId);
        authenticateService.invalidate();
        return userDTO;
    }

    @RequestMapping(path = "/change/password", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public ResultDTO changePassword(@RequestBody UserPasswordDTO userPasswordDTO){
        Long userId = sessionDataManager.getUserData().getUserId();
        ResultDTO resultDTO = userService.changePassword(userPasswordDTO, userId);
        authenticateService.invalidate();
        return resultDTO;
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET, produces = "application/json")
    public UserDTO getCurrent(){
        Long userId = sessionDataManager.getUserData().getUserId();
        return userService.getById(userId);
    }

    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public void deleteCurrent(){
        Long userId = sessionDataManager.getUserData().getUserId();
        userService.deleteById(userId);
        authenticateService.invalidate();
    }
}
