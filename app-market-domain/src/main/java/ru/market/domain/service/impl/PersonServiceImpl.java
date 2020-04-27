package ru.market.domain.service.impl;

import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.PersonConverter;
import ru.market.domain.data.Person;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.repository.common.PersonRepository;
import ru.market.domain.service.IPersonService;

import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonNoIdDTO;

public class PersonServiceImpl implements IPersonService {
    private PersonRepository personRepository;
    private PersonConverter personConverter;
    private CommonValidator<Person> validator;

    public PersonServiceImpl(PersonRepository personRepository,
                             PersonConverter personConverter,
                             CommonValidator<Person> validator) {

        this.personRepository = personRepository;
        this.personConverter = personConverter;
        this.validator = validator;
    }

    @Transactional
    @Override
    public PersonDTO create(PersonNoIdDTO personNoIdDTO) {
        return updateAndSave(personNoIdDTO);
    }

    @Transactional
    @Override
    public PersonDTO update(PersonDTO personDTO) {
        return updateAndSave(personDTO);
    }

    private PersonDTO updateAndSave(PersonNoIdDTO personDTO){
        if(personDTO == null){
            return null;
        }

        Person person = personConverter.convertToEntity(personDTO);

        validator.validate(person);

        person = personRepository.saveAndFlush(person);
        return personConverter.convertToDTO(person);
    }

    @Override
    public PersonDTO getById(Long id) {
        Person person = getPersonById(id);
        return personConverter.convertToDTO(person);
    }

    @Override
    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Person with id %d not found", id))
        );
    }
}
