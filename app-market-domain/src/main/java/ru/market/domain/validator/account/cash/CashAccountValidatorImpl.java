package ru.market.domain.validator.account.cash;

import ru.market.domain.data.CashAccount;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.account.CashAccountRepository;
import ru.market.domain.validator.account.AccountValidatorImpl;

public class CashAccountValidatorImpl<E extends CashAccount> extends AccountValidatorImpl<E>
        implements CashAccountValidator<E> {

    private CashAccountRepository cashAccountRepository;

    public CashAccountValidatorImpl(CashAccountRepository cashAccountRepository) {
        this.cashAccountRepository = cashAccountRepository;
    }

    @Override
    public void validateUniqueName(E cashAccount) throws ValidateException {
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
