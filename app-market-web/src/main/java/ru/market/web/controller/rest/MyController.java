package ru.market.web.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @RequestMapping(path = "/mi", method = RequestMethod.GET)
    public String mi(){
        return "ola-ola-ola";
    }
}
