package ru.market.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String indexPage(){
        return "index";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginPage(){
        return "login";
    }

    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    public String registrationPage(){
        return "registration";
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public String userPage(){
        return "user";
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String adminPage(){
        return "admin";
    }
}
