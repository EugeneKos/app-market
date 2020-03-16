package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.market.auth.api.AuthenticateService;
import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonWithPasswordDTO;
import ru.market.domain.service.IPersonService;

@RestController
public class PersonController {
    private IPersonService personService;
    private AuthenticateService authenticateService;

    @Autowired
    public PersonController(IPersonService personService, AuthenticateService authenticateService) {
        this.personService = personService;
        this.authenticateService = authenticateService;
    }

    @RequestMapping(path = "/person", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public PersonDTO create(@RequestBody PersonWithPasswordDTO personWithPasswordDTO){
        return personService.create(personWithPasswordDTO);
    }

    @RequestMapping(path = "/person", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public PersonDTO update(@RequestBody PersonWithPasswordDTO personWithPasswordDTO){
        return personService.update(personWithPasswordDTO);
    }

    @RequestMapping(path = "/person/{id}", method = RequestMethod.GET, produces = "application/json")
    public PersonDTO getById(@PathVariable(name = "id") Long id){
        return personService.getById(id);
    }

    @RequestMapping(path = "/person/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable(name = "id") Long id){
        personService.deleteById(id);
        authenticateService.invalidate();
    }
}
