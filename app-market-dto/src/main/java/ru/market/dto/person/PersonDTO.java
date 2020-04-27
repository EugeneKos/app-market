package ru.market.dto.person;

public class PersonDTO extends PersonNoIdDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
