package ru.market.domain.event;

import org.springframework.context.ApplicationEvent;

public class AccountDeleteEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1897801226021591832L;

    private Long accountId;

    public AccountDeleteEvent(Object source, Long accountId){
        super(source);
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
