package ru.market.domain.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import ru.market.domain.service.ICardService;
import ru.market.domain.service.ICashService;

public class PersonDeleteEventListener implements ApplicationListener<PersonDeleteEvent> {
    private ICardService cardService;
    private ICashService cashService;

    @Autowired
    public PersonDeleteEventListener(ICardService cardService, ICashService cashService) {
        this.cardService = cardService;
        this.cashService = cashService;
    }

    @Override
    public void onApplicationEvent(PersonDeleteEvent personDeleteEvent) {
        Long personId = personDeleteEvent.getPersonId();
        cardService.deleteAllByPersonId(personId);
        cashService.deleteAllByPersonId(personId);
    }
}
