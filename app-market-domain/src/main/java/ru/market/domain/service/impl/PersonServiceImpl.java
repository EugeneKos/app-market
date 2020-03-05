package ru.market.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.PersonConverter;
import ru.market.domain.data.Person;
import ru.market.domain.data.dto.PersonDTO;
import ru.market.domain.exception.MustIdException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.repository.PersonRepository;
import ru.market.domain.service.IPersonService;

@Service
public class PersonServiceImpl implements IPersonService {
    private PersonRepository personRepository;
    private PersonConverter personConverter;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, PersonConverter personConverter) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
    }

    @Override
    @Transactional
    public PersonDTO create(PersonDTO personDTO) {
        return update(personDTO, false);
    }

    @Override
    @Transactional
    public PersonDTO update(PersonDTO personDTO) {
        return update(personDTO, true);
    }

    private PersonDTO update(PersonDTO personDTO, boolean isMustId){
        if(personDTO == null){
            return null;
        }

        Person person = personConverter.convertToPerson(personDTO);

        assertExistById(person, isMustId);
        assertUniqueByUsername(person);

        person = personRepository.saveAndFlush(person);
        return personConverter.convertToPersonDTO(person);
    }

    private void assertExistById(Person person, boolean isMustId){
        if(!isMustId){
            person.setId(null);
            return;
        }
        if(person.getId() == null){
            throw new MustIdException("Person id should be given");
        }

        personRepository.findById(person.getId()).orElseThrow(
                () -> new NotFoundException("Person with id " + person.getId() + " not found")
        );
    }

    private void assertUniqueByUsername(Person person){
        Person founded = personRepository.findByUsername(person.getUsername());
        if(founded != null && !founded.getId().equals(person.getId())){
            throw new NotUniqueException("Person with login: " + founded.getUsername() + " already exist");
        }
    }

    @Override
    public PersonDTO getById(Long id) {
        Person person = personRepository.findById(id).orElse(null);
        return personConverter.convertToPersonDTO(person);
    }

    @Override
    public PersonDTO getByUsername(String username) {
        Person person = personRepository.findByUsername(username);
        return personConverter.convertToPersonDTO(person);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        personRepository.deleteByUsername(username);
    }
}
