package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.domain.service.ICashAccountService;

import java.util.Set;

@UrlFilter(urlPatterns = "/cash*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/cash",
                methods = {ExcludeRequestMethod.Method.PUT, ExcludeRequestMethod.Method.GET})}
)
public class CashAccountRequestFilter extends AccountRequestFilter {
    private PersonDataManagement personDataManagement;
    private ICashAccountService cashAccountService;

    public CashAccountRequestFilter(PersonDataManagement personDataManagement, ICashAccountService cashAccountService) {
        this.personDataManagement = personDataManagement;
        this.cashAccountService = cashAccountService;
    }

    @Override
    Set<Long> getAllAccountId() {
        Long personId = personDataManagement.getPerson().getId();
        return cashAccountService.getAllAccountIdByPersonId(personId);
    }

    @Override
    String getAccountPathIdentifier() {
        return "cash/";
    }
}
