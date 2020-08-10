package ru.market.domain.validator.money;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.domain.data.MoneyAccount;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.MoneyAccountRepository;
import ru.market.domain.validator.utils.ValidatorUtils;
import ru.market.domain.validator.CommonValidator;

import java.math.BigDecimal;

public class MoneyAccountValidator implements CommonValidator<MoneyAccount> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyAccountValidator.class);

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
        LOGGER.info("Валидация баланса денежного счета");
        BigDecimal balance = moneyAccount.getBalance();

        if(balance == null){
            throw new ValidateException("Баланс счета не должен быть пустым.");
        }
        LOGGER.debug("MoneyAccount [validate balance] balance = {}", balance);

        if(ValidatorUtils.isMatchMoney(balance.toString())){
            LOGGER.info("Валидация баланса денежного счета прошла успешно");
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
        LOGGER.info("Валидация наименования денежного счета");
        LOGGER.debug("MoneyAccount [unique name] name = {}", moneyAccount.getName());
        MoneyAccount founded = moneyAccountRepository.findByNameAndPerson(moneyAccount.getName(), moneyAccount.getPerson());
        if(founded != null){
            throw new NotUniqueException("Money account with name: " + founded.getName() + " already exist");
        }
        LOGGER.info("Валидация наименования денежного счета прошла успешно");
    }
}
