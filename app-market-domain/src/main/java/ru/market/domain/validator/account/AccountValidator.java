package ru.market.domain.validator.account;

import ru.market.domain.data.BankAccount;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.service.utils.ServiceUtils;
import ru.market.domain.validator.CommonValidator;

public class AccountValidator<E extends BankAccount> implements CommonValidator<E> {
    @Override
    public void validate(E bankAccount) throws ValidateException {
        validateBalance(bankAccount);
    }

    void validateBalance(E bankAccount) throws ValidateException {
        String balance = bankAccount.getBalance();

        if(balance == null || balance.isEmpty()){
            throw new ValidateException("Баланс счета не должен быть пустым.");
        }

        if(ServiceUtils.isMatchMoney(balance)){
            return;
        }

        throw new ValidateException("Баланс счета заполнен некорректен");
    }
}
