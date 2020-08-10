package ru.market.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import ru.market.domain.converter.PersonConverter;
import ru.market.domain.data.Person;
import ru.market.domain.exception.BadRequestException;
import ru.market.domain.exception.NotFoundException;
import ru.market.domain.validator.CommonValidator;
import ru.market.domain.repository.PersonRepository;
import ru.market.domain.service.IPersonService;

import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonNoIdDTO;

public class PersonServiceImpl implements IPersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

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
        LOGGER.info("Сохранение персональных данных пользователя");
        return updateAndSave(personNoIdDTO);
    }

    @Transactional
    @Override
    public PersonDTO update(PersonDTO personDTO) {
        LOGGER.info("Обновление персональных данных пользователя");
        return updateAndSave(personDTO);
    }

    private PersonDTO updateAndSave(PersonNoIdDTO personDTO){
        if(personDTO == null){
            throw new BadRequestException("Персональные данные пользователя не заданы");
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
        LOGGER.info("Получение персональных данных пользователя по id = {}", id);
        return personRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Person with id %d not found", id))
        );
    }
}
