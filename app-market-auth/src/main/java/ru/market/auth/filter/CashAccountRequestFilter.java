package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;

import ru.market.data.session.api.UserDataManager;
import ru.market.domain.service.ICashAccountService;

import java.util.Set;

@UrlFilter(urlPatterns = "/cash*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/cash",
                methods = {ExcludeRequestMethod.Method.PUT, ExcludeRequestMethod.Method.GET})}
)
public class CashAccountRequestFilter extends AccountRequestFilter {
    private UserDataManager userDataManager;
    private ICashAccountService cashAccountService;

    public CashAccountRequestFilter(UserDataManager userDataManager, ICashAccountService cashAccountService) {
        this.userDataManager = userDataManager;
        this.cashAccountService = cashAccountService;
    }

    @Override
    Set<Long> getAllAccountId() {
        Long personId = userDataManager.getUserData().getPersonId();
        return cashAccountService.getAllAccountIdByPersonId(personId);
    }

    @Override
    String getAccountPathIdentifier() {
        return "cash/";
    }
}
