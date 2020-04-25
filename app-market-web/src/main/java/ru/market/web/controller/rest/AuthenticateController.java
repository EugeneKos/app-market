package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.auth.api.AuthenticateService;

import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;

@RestController
public class AuthenticateController {
    private AuthenticateService authenticateService;

    @Autowired
    public AuthenticateController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @RequestMapping(path = "/auth", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public ResultDTO authenticate(@RequestBody UsernamePasswordDTO usernamePasswordDTO){
        return authenticateService.authenticate(usernamePasswordDTO);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public void logout(){
        authenticateService.invalidate();
    }
}
