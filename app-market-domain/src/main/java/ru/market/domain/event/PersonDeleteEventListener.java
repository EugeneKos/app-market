package ru.market.domain.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import ru.market.domain.service.IBankAccountService;

public class PersonDeleteEventListener implements ApplicationListener<PersonDeleteEvent> {
    private IBankAccountService bankAccountService;

    @Autowired
    public PersonDeleteEventListener(IBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public void onApplicationEvent(PersonDeleteEvent event) {
        Long personId = event.getPersonId();
        bankAccountService.deleteAllByPersonId(personId);
    }
}
