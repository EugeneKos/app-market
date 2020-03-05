package ru.market.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.market.domain.data.dto.PersonDTO;
import ru.market.domain.data.dto.PersonWithPasswordDTO;
import ru.market.domain.service.IPersonService;

@RestController
public class PersonController {
    private IPersonService personService;

    @Autowired
    public PersonController(IPersonService personService) {
        this.personService = personService;
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
    }
}
