package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.domain.service.ICashService;

import java.util.Set;

@UrlFilter(urlPatterns = "/cash*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/cash", methods = ExcludeRequestMethod.Method.PUT),
                                @ExcludeRequestMethod(url = "/cash", methods = ExcludeRequestMethod.Method.GET)}
)
public class CashRequestFilter extends BankRequestFilter {
    private PersonDataManagement personDataManagement;
    private ICashService cashService;

    public CashRequestFilter(PersonDataManagement personDataManagement, ICashService cashService) {
        this.personDataManagement = personDataManagement;
        this.cashService = cashService;
    }

    @Override
    Set<Long> getAllBankId() {
        Long personId = personDataManagement.getPerson().getId();
        return cashService.getAllCashIdByPersonId(personId);
    }

    @Override
    String getBankPathIdentifier() {
        return "cash/";
    }
}
