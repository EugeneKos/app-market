package ru.market.domain.validator.account.card;

import ru.market.domain.data.CardAccount;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.validator.account.AccountValidator;

public interface CardAccountValidator<E extends CardAccount> extends AccountValidator<E> {
    void validateNumber(E cardAccount) throws ValidateException;
    void validateUniqueNumber(E cardAccount) throws ValidateException;
}
