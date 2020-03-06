package ru.market.domain.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.domain.data.Person;
import ru.market.dto.person.PersonDTO;

@Service
public class PersonConverter {
    private DozerBeanMapper mapper;

    @Autowired
    public PersonConverter(DozerBeanMapper mapper) {
        this.mapper = mapper;
    }

    public Person convertToPerson(PersonDTO personDTO){
        if(personDTO == null){
            return null;
        }
        return mapper.map(personDTO, Person.class);
    }

    public PersonDTO convertToPersonDTO(Person person){
        if(person == null){
            return null;
        }
        return mapper.map(person, PersonDTO.class);
    }
}
