package ru.market.domain.validator.account;

import ru.market.domain.data.BankAccount;
import ru.market.domain.exception.ValidateException;

public interface AccountValidator<E extends BankAccount> {
    void validateBalance(E bankAccount) throws ValidateException;
}
