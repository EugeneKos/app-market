package ru.market.domain.validator.account.card;

import ru.market.domain.data.CardAccount;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.account.CardAccountRepository;
import ru.market.domain.validator.account.AccountValidatorImpl;

public class CardAccountValidatorImpl<E extends CardAccount> extends AccountValidatorImpl<E>
        implements CardAccountValidator<E> {

    private CardAccountRepository cardAccountRepository;

    public CardAccountValidatorImpl(CardAccountRepository cardAccountRepository) {
        this.cardAccountRepository = cardAccountRepository;
    }

    @Override
    public void validateNumber(E cardAccount) throws ValidateException {
        String number = cardAccount.getNumber();

        if(number == null || number.isEmpty()){
            throw new ValidateException("Номер карты не должен быть пустым");
        }
    }

    @Override
    public void validateUniqueNumber(E cardAccount) throws ValidateException {
        try {
            assertUniqueByNumber(cardAccount);
        } catch (Exception e){
            throw new ValidateException("Ошибка вылидации карты", e);
        }
    }

    private void assertUniqueByNumber(CardAccount cardAccount) throws NotUniqueException {
        CardAccount founded = cardAccountRepository.findByNumber(cardAccount.getNumber());
        if(founded != null && !founded.getId().equals(cardAccount.getId())){
            throw new NotUniqueException("Card with number: " + founded.getNumber() + " already exist");
        }
    }
}
