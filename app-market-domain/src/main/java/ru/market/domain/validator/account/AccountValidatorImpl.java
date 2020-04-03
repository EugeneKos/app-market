package ru.market.domain.validator.account;

import ru.market.domain.data.BankAccount;
import ru.market.domain.exception.ValidateException;

public class AccountValidatorImpl<E extends BankAccount> implements AccountValidator<E> {
    @Override
    public void validateBalance(E bankAccount) throws ValidateException {
        String balance = bankAccount.getBalance();

        if(balance == null || balance.isEmpty()){
            throw new ValidateException("Баланс счета не должен быть пустым.");
        }

        if(balance.matches("[0-9]+")){
            return;
        }

        if(balance.matches("\\d+[.]\\d{1,2}")){
            return;
        }

        throw new ValidateException("Баланс не корректен");
    }
}
