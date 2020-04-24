package ru.market.domain.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import ru.market.domain.service.IOperationService;

public class AccountDeleteEventListener implements ApplicationListener<AccountDeleteEvent> {
    private IOperationService operationService;

    @Autowired
    public AccountDeleteEventListener(IOperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    public void onApplicationEvent(AccountDeleteEvent event) {
        operationService.deleteAllByAccountId(event.getAccountId());
    }
}
