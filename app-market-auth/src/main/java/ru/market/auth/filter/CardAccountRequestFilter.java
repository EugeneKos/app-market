package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.domain.service.ICardAccountService;

import java.util.Set;

@UrlFilter(urlPatterns = "/card*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/card",
                methods = {ExcludeRequestMethod.Method.PUT, ExcludeRequestMethod.Method.GET})}
)
public class CardAccountRequestFilter extends AccountRequestFilter {
    private PersonDataManagement personDataManagement;
    private ICardAccountService cardAccountService;

    public CardAccountRequestFilter(PersonDataManagement personDataManagement, ICardAccountService cardAccountService) {
        this.personDataManagement = personDataManagement;
        this.cardAccountService = cardAccountService;
    }

    @Override
    Set<Long> getAllAccountId() {
        Long personId = personDataManagement.getPerson().getId();
        return cardAccountService.getAllAccountIdByPersonId(personId);
    }

    @Override
    String getAccountPathIdentifier() {
        return "card/";
    }
}
