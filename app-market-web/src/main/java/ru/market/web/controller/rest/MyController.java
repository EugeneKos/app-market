package ru.market.web.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.dto.person.PersonDTO;
import ru.market.web.session.SessionContext;

@RestController
public class MyController {
    @RequestMapping(path = "/mi", method = RequestMethod.GET)
    public PersonDTO mi(){
        return SessionContext.getPerson();
    }
}
