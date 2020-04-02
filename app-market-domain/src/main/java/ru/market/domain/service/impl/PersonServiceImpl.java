package ru.market.domain.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.PersonConverter;
import ru.market.domain.data.Person;
import ru.market.domain.event.PersonDeleteEvent;
import ru.market.dto.person.PersonDTO;
import ru.market.domain.exception.MustIdException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.repository.PersonRepository;
import ru.market.domain.service.IPersonService;
import ru.market.dto.person.PersonWithPasswordDTO;

public class PersonServiceImpl implements IPersonService {
    private PersonRepository personRepository;
    private PersonConverter personConverter;

    private ApplicationEventPublisher eventPublisher;

    public PersonServiceImpl(PersonRepository personRepository, PersonConverter personConverter) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
    }

    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
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

        assertExistById(person);
        assertUniqueByUsername(person);

        person = personRepository.saveAndFlush(person);
        return personConverter.convertToBasedDTO(person);
    }

    private void assertExistById(Person person){
        if(person.getId() == null){
            return;
        }

        personRepository.findById(person.getId()).orElseThrow(
                () -> new NotFoundException("Person with id " + person.getId() + " not found")
        );
    }

    private void assertUniqueByUsername(Person person){
        Person founded = personRepository.findByUsername(person.getUsername());
        if(founded != null && !founded.getId().equals(person.getId())){
            throw new NotUniqueException("Person with username: " + founded.getUsername() + " already exist");
        }
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
        eventPublisher.publishEvent(new PersonDeleteEvent(this, id));
        personRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        personRepository.deleteByUsername(username);
    }
}
