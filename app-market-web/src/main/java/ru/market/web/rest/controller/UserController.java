package ru.market.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.market.domain.data.dto.UserDTO;
import ru.market.domain.service.IUserService;

@RestController
@RequestMapping(path = "/rest/users", produces = "application/json")
public class UserController {
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/get/{login}")
    public UserDTO getUserById(@PathVariable("login") String login){
        return userService.getByLogin(login);
    }

    @RequestMapping(path = "/create")
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        return userService.create(userDTO);
    }
}
