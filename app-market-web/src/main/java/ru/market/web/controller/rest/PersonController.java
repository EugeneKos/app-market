package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.market.auth.api.AuthenticateService;
import ru.market.data.session.api.PersonDataManagement;
import ru.market.data.session.api.RequestBodyManagement;
import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonWithPasswordDTO;
import ru.market.domain.service.IPersonService;

@RestController
public class PersonController {
    private IPersonService personService;
    private AuthenticateService authenticateService;
    private RequestBodyManagement requestBodyManagement;
    private PersonDataManagement personDataManagement;

    @Autowired
    public PersonController(IPersonService personService,
                            AuthenticateService authenticateService,
                            RequestBodyManagement requestBodyManagement,
                            PersonDataManagement personDataManagement) {

        this.personService = personService;
        this.authenticateService = authenticateService;
        this.requestBodyManagement = requestBodyManagement;
        this.personDataManagement = personDataManagement;
    }

    @RequestMapping(path = "/person", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public PersonDTO create(@RequestBody PersonWithPasswordDTO personWithPasswordDTO){
        return personService.create(personWithPasswordDTO);
    }

    @RequestMapping(path = "/person", method = RequestMethod.POST, produces = "application/json")
    public PersonDTO update(){
        return personService.update(requestBodyManagement.getCurrentRequestBody());
    }

    @RequestMapping(path = "/person/{id}", method = RequestMethod.GET, produces = "application/json")
    public PersonDTO getById(@PathVariable(name = "id") Long id){
        return personService.getById(id);
    }

    @RequestMapping(path = "/person", method = RequestMethod.GET, produces = "application/json")
    public PersonDTO getCurrent(){
        Long personId = personDataManagement.getPerson().getId();
        return personService.getById(personId);
    }

    @RequestMapping(path = "/person/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable(name = "id") Long id){
        personService.deleteById(id);
        authenticateService.invalidate();
    }

    @RequestMapping(path = "/person", method = RequestMethod.DELETE)
    public void deleteCurrent(){
        Long personId = personDataManagement.getPerson().getId();
        personService.deleteById(personId);
        authenticateService.invalidate();
    }
}
