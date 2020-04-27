package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.market.data.session.api.RequestBodyManagement;
import ru.market.data.session.api.UserDataManager;
import ru.market.dto.person.PersonDTO;
import ru.market.domain.service.IPersonService;

@RestController
public class PersonController {
    private IPersonService personService;
    private RequestBodyManagement requestBodyManagement;
    private UserDataManager userDataManager;

    @Autowired
    public PersonController(IPersonService personService,
                            RequestBodyManagement requestBodyManagement,
                            UserDataManager userDataManager) {

        this.personService = personService;
        this.requestBodyManagement = requestBodyManagement;
        this.userDataManager = userDataManager;
    }

    @RequestMapping(path = "/person", method = RequestMethod.POST, produces = "application/json")
    public PersonDTO update(){
        return personService.update(requestBodyManagement.getCurrentRequestBody());
    }

    @RequestMapping(path = "/person", method = RequestMethod.GET, produces = "application/json")
    public PersonDTO getCurrent(){
        Long personId = userDataManager.getUserData().getPersonId();
        return personService.getById(personId);
    }
}
