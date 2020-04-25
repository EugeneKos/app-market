package ru.market.domain.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.PersonConverter;
import ru.market.domain.data.Person;
import ru.market.domain.validator.CommonValidator;
import ru.market.dto.person.PersonDTO;
import ru.market.domain.exception.MustIdException;
import ru.market.domain.repository.common.PersonRepository;
import ru.market.domain.service.IPersonService;
import ru.market.dto.person.PersonWithPasswordDTO;

public class PersonServiceImpl implements IPersonService {
    private PersonRepository personRepository;
    private PersonConverter personConverter;
    private CommonValidator<Person> validator;

    private PasswordEncoder passwordEncoder;

    public PersonServiceImpl(PersonRepository personRepository,
                             PersonConverter personConverter,
                             CommonValidator<Person> validator) {

        this.personRepository = personRepository;
        this.personConverter = personConverter;
        this.validator = validator;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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

        Person person = personConverter.convertToEntity(personDTO);
        if(!isMustId){
            person.setId(null);
        }
        if(isMustId && person.getId() == null){
            throw new MustIdException("Person id should be given");
        }

        validator.validate(person);

        person.setPassword(passwordEncoder.encode(person.getPassword()));

        person = personRepository.saveAndFlush(person);
        return personConverter.convertToBasedDTO(person);
    }

    @Override
    public PersonDTO getById(Long id) {
        Person person = personRepository.findById(id).orElse(null);
        return personConverter.convertToBasedDTO(person);
    }

    @Override
    public PersonDTO getByUsername(String username) {
        Person person = personRepository.findByUsername(username);
        return personConverter.convertToBasedDTO(person);
    }

    @Override
    public PersonWithPasswordDTO getByUserNameWithPassword(String username) {
        Person person = personRepository.findByUsername(username);
        return personConverter.convertToDTO(person);
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
