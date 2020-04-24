package ru.market.domain.validator.account;

import ru.market.domain.data.CardAccount;
import ru.market.domain.exception.NotUniqueException;
import ru.market.domain.exception.ValidateException;
import ru.market.domain.repository.account.CardAccountRepository;
import ru.market.domain.validator.CommonValidator;

public class CardAccountValidator extends AccountValidator<CardAccount> implements CommonValidator<CardAccount> {
    private CardAccountRepository cardAccountRepository;

    public CardAccountValidator(CardAccountRepository cardAccountRepository) {
        this.cardAccountRepository = cardAccountRepository;
    }

    @Override
    public void validate(CardAccount cardAccount) throws ValidateException {
        validateNumber(cardAccount);
        validateBalance(cardAccount);
        validateUniqueNumber(cardAccount);
    }

    private void validateNumber(CardAccount cardAccount) throws ValidateException {
        String number = cardAccount.getNumber();

        if(number == null || number.isEmpty()){
            throw new ValidateException("Номер карты не должен быть пустым");
        }

        if(!number.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")){
            throw new ValidateException(
                    "Номер карты не соответствует формату: ****-****-****-**** где (*) цифровое значение"
            );
        }
    }

    private void validateUniqueNumber(CardAccount cardAccount) throws ValidateException {
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
