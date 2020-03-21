package ru.market.domain.event;

import org.springframework.context.ApplicationEvent;

public class PersonDeleteEvent extends ApplicationEvent {
    private static final long serialVersionUID = -59229820381384190L;

    private Long personId;

    public PersonDeleteEvent(Object source, Long personId) {
        super(source);
        this.personId = personId;
    }

    public Long getPersonId() {
        return personId;
    }
}
