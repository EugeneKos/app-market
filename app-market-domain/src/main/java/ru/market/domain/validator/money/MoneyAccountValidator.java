package ru.market.domain.validator.money;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.MoneyAccountRepository;
import ru.market.domain.validator.utils.ValidatorUtils;
import ru.market.domain.validator.CommonValidator;

import java.math.BigDecimal;

public class MoneyAccountValidator implements CommonValidator<MoneyAccount> {
    private MoneyAccountRepository moneyAccountRepository;

    public MoneyAccountValidator(MoneyAccountRepository moneyAccountRepository) {
        this.moneyAccountRepository = moneyAccountRepository;
    }

    @Override
    public void validate(MoneyAccount moneyAccount) throws ValidateException {
        validateBalance(moneyAccount);
        validateUniqueName(moneyAccount);
    }

    private void validateBalance(MoneyAccount moneyAccount) throws ValidateException {
        BigDecimal balance = moneyAccount.getBalance();

        if(balance == null){
            throw new ValidateException("Баланс счета не должен быть пустым.");
        }

        if(ValidatorUtils.isMatchMoney(balance.toString())){
            return;
        }

        throw new ValidateException("Баланс счета заполнен некорректен");
    }

    private void validateUniqueName(MoneyAccount moneyAccount) throws ValidateException {
        try {
            assertUniqueByName(moneyAccount);
        } catch (Exception e){
            throw new ValidateException("Ошибка валидации денежного счета", e);
        }
    }

    private void assertUniqueByName(MoneyAccount moneyAccount){
        MoneyAccount founded = moneyAccountRepository.findByNameAndPerson(moneyAccount.getName(), moneyAccount.getPerson());
        if(founded != null){
            throw new NotUniqueException("Money account with name: " + founded.getName() + " already exist");
        }
    }
}
