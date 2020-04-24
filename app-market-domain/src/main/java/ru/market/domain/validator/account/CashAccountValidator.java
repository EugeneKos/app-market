package ru.market.domain.validator.account;

import ru.market.domain.data.CashAccount;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.account.CashAccountRepository;
import ru.market.domain.validator.CommonValidator;

public class CashAccountValidator extends AccountValidator<CashAccount> implements CommonValidator<CashAccount> {
    private CashAccountRepository cashAccountRepository;

    public CashAccountValidator(CashAccountRepository cashAccountRepository) {
        this.cashAccountRepository = cashAccountRepository;
    }

    @Override
    public void validate(CashAccount cashAccount) throws ValidateException {
        validateBalance(cashAccount);
        validateUniqueName(cashAccount);
    }

    private void validateUniqueName(CashAccount cashAccount) throws ValidateException {
        try {
            assertUniqueByName(cashAccount);
        } catch (Exception e){
            throw new ValidateException("Ошибка валидации наличных", e);
        }
    }

    private void assertUniqueByName(CashAccount cashAccount) throws NotUniqueException {
        CashAccount founded = cashAccountRepository.findByName(cashAccount.getName());
        if(founded != null && !founded.getId().equals(cashAccount.getId())){
            throw new NotUniqueException("Cash with name: " + founded.getName() + " already exist");
        }
    }
}
