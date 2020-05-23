package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;
import ru.market.data.session.api.SessionDataManager;
import ru.market.domain.service.IMoneyAccountService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@UrlFilter(urlPatterns = "/money-account*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/money-account",
                methods = {ExcludeRequestMethod.Method.PUT, ExcludeRequestMethod.Method.GET})}
)
public class MoneyAccountRequestFilter implements AuthFilter {
    private SessionDataManager sessionDataManager;
    private IMoneyAccountService moneyAccountService;

    public MoneyAccountRequestFilter(SessionDataManager sessionDataManager, IMoneyAccountService moneyAccountService) {
        this.sessionDataManager = sessionDataManager;
        this.moneyAccountService = moneyAccountService;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Long personId = sessionDataManager.getUserData().getPersonId();
        Set<Long> allMoneyAccountId = moneyAccountService.getAllIdByPersonId(personId);

        String servletPath = request.getServletPath();
        String requestMethod = request.getMethod();

        boolean isWell;

        switch (requestMethod){
            case "GET":{
                isWell = Utils.containsIdInPath(servletPath, "money-account/", allMoneyAccountId);
                break;
            }
            case "DELETE":{
                isWell = Utils.containsIdInPath(servletPath, "money-account/", allMoneyAccountId);
                break;
            }
            default:
                isWell = false;
        }

        if(isWell){
            authChain.doFilter(request, response, filterChain);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }

    }
}
