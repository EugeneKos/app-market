package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;

import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.service.ICashAccountService;

import java.util.Set;

@UrlFilter(urlPatterns = "/cash*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/cash",
                methods = {ExcludeRequestMethod.Method.PUT, ExcludeRequestMethod.Method.GET})}
)
public class CashAccountRequestFilter extends AccountRequestFilter {
    private SessionDataManager sessionDataManager;
    private ICashAccountService cashAccountService;

    public CashAccountRequestFilter(SessionDataManager sessionDataManager, ICashAccountService cashAccountService) {
        this.sessionDataManager = sessionDataManager;
        this.cashAccountService = cashAccountService;
    }

    @Override
    Set<Long> getAllAccountId() {
        Long personId = sessionDataManager.getUserData().getPersonId();
        return cashAccountService.getAllAccountIdByPersonId(personId);
    }

    @Override
    String getAccountPathIdentifier() {
        return "cash/";
    }
}
