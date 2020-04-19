package ru.market.domain.validator.account;

import ru.market.domain.data.BankAccount;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.service.utils.ServiceUtils;

public class AccountValidatorImpl<E extends BankAccount> implements AccountValidator<E> {
    @Override
    public void validateBalance(E bankAccount) throws ValidateException {
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
