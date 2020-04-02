package ru.market.domain.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import ru.market.domain.service.ICardAccountService;
import ru.market.domain.service.ICashAccountService;

public class PersonDeleteEventListener implements ApplicationListener<PersonDeleteEvent> {
    private ICardAccountService cardAccountService;
    private ICashAccountService cashAccountService;

    @Autowired
    public PersonDeleteEventListener(ICardAccountService cardAccountService, ICashAccountService cashAccountService) {
        this.cardAccountService = cardAccountService;
        this.cashAccountService = cashAccountService;
    }

    @Override
    public void onApplicationEvent(PersonDeleteEvent personDeleteEvent) {
        Long personId = personDeleteEvent.getPersonId();
        cardAccountService.deleteAllByPersonId(personId);
        cashAccountService.deleteAllByPersonId(personId);
    }
}
