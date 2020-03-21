package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.domain.service.ICardService;

import java.util.Set;

@UrlFilter(urlPatterns = "/card*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/card", methods = ExcludeRequestMethod.Method.PUT),
                                @ExcludeRequestMethod(url = "/card", methods = ExcludeRequestMethod.Method.GET)}
)
public class CardRequestFilter extends BankRequestFilter {
    private PersonDataManagement personDataManagement;
    private ICardService cardService;

    public CardRequestFilter(PersonDataManagement personDataManagement, ICardService cardService) {
        this.personDataManagement = personDataManagement;
        this.cardService = cardService;
    }

    @Override
    Set<Long> getAllBankId() {
        Long personId = personDataManagement.getPerson().getId();
        return cardService.getAllCardIdByPersonId(personId);
    }

    @Override
    String getBankPathIdentifier() {
        return "card/";
    }
}
