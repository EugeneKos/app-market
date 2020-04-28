package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.market.data.session.api.SessionDataManager;
import ru.market.dto.person.PersonDTO;
import ru.market.domain.service.IPersonService;

@RestController
public class PersonController {
    private IPersonService personService;
    private SessionDataManager sessionDataManager;

    @Autowired
    public PersonController(IPersonService personService, SessionDataManager sessionDataManager) {

        this.personService = personService;
        this.sessionDataManager = sessionDataManager;
    }

    @RequestMapping(path = "/person", method = RequestMethod.POST, produces = "application/json")
    public PersonDTO update(){
        return personService.update(sessionDataManager.getCurrentRequestBody());
    }

    @RequestMapping(path = "/person", method = RequestMethod.GET, produces = "application/json")
    public PersonDTO getCurrent(){
        Long personId = sessionDataManager.getUserData().getPersonId();
        return personService.getById(personId);
    }
}
