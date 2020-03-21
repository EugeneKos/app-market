package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.domain.service.ICashService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Set;

@UrlFilter(urlPatterns = "/cash*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/cash", methods = ExcludeRequestMethod.Method.PUT),
                                @ExcludeRequestMethod(url = "/cash", methods = ExcludeRequestMethod.Method.GET)}
)
public class CashRequestFilter implements AuthFilter {
    private PersonDataManagement personDataManagement;
    private ICashService cashService;

    public CashRequestFilter(PersonDataManagement personDataManagement, ICashService cashService) {
        this.personDataManagement = personDataManagement;
        this.cashService = cashService;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Long personId = personDataManagement.getPerson().getId();
        Set<Long> allCashIdByPersonId = cashService.getAllCashIdByPersonId(personId);

        String servletPath = request.getServletPath();
        String requestMethod = request.getMethod();

        boolean isWell;

        switch (requestMethod){
            case "GET":{
                isWell = containsCashId(servletPath, allCashIdByPersonId);
                break;
            }
            case "DELETE":{
                isWell = containsCashId(servletPath, allCashIdByPersonId);
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

    private boolean containsCashId(String servletPath, Set<Long> cashIdSet){
        int lastIndexOfSlash = servletPath.lastIndexOf("cash/");
        if(lastIndexOfSlash == -1){
            return false;
        }
        lastIndexOfSlash = lastIndexOfSlash + 5;

        String possibleCashId = servletPath.substring(lastIndexOfSlash, servletPath.length());

        if(!possibleCashId.matches("\\d+")){
            return false;
        }

        return cashIdSet.contains(Long.parseLong(possibleCashId));
    }
}
