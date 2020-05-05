package ru.market.domain.validator.account;

import ru.market.domain.data.BankAccount;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.service.utils.ServiceUtils;
import ru.market.domain.validator.CommonValidator;

import java.math.BigDecimal;

public class AccountValidator<E extends BankAccount> implements CommonValidator<E> {
    @Override
    public void validate(E bankAccount) throws ValidateException {
        validateBalance(bankAccount);
    }

    void validateBalance(E bankAccount) throws ValidateException {
        BigDecimal balance = bankAccount.getBalance();

        if(balance == null){
            throw new ValidateException("Баланс счета не должен быть пустым.");
        }

        if(ServiceUtils.isMatchMoney(balance.toString())){
            return;
        }

        throw new ValidateException("Баланс счета заполнен некорректен");
    }
}
