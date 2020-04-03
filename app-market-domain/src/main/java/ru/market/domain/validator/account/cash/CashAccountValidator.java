package ru.market.domain.validator.account.cash;

import ru.market.domain.data.CashAccount;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.validator.account.AccountValidator;

public interface CashAccountValidator<E extends CashAccount> extends AccountValidator<E> {
    void validateUniqueName(E cashAccount) throws ValidateException;
}
