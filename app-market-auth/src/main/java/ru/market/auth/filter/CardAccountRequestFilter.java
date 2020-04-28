package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;

import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.service.ICardAccountService;

import java.util.Set;

@UrlFilter(urlPatterns = "/card*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/card",
                methods = {ExcludeRequestMethod.Method.PUT, ExcludeRequestMethod.Method.GET})}
)
public class CardAccountRequestFilter extends AccountRequestFilter {
    private SessionDataManager sessionDataManager;
    private ICardAccountService cardAccountService;

    public CardAccountRequestFilter(SessionDataManager sessionDataManager, ICardAccountService cardAccountService) {
        this.sessionDataManager = sessionDataManager;
        this.cardAccountService = cardAccountService;
    }

    @Override
    Set<Long> getAllAccountId() {
        Long personId = sessionDataManager.getUserData().getPersonId();
        return cardAccountService.getAllAccountIdByPersonId(personId);
    }

    @Override
    String getAccountPathIdentifier() {
        return "card/";
    }
}
