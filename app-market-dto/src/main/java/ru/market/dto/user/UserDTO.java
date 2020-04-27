package ru.market.dto.user;

import ru.market.dto.person.PersonDTO;

public class UserDTO extends UserUsernameDTO {
    private Long id;

    private PersonDTO person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }
}
