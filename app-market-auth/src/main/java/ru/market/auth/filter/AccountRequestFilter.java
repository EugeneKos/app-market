package ru.market.auth.filter;

import ru.market.auth.api.AuthFilterChain;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Set;

public abstract class AccountRequestFilter implements AuthFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Set<Long> allAccountId = getAllAccountId();

        String servletPath = request.getServletPath();
        String requestMethod = request.getMethod();
        String accountPathIdentifier = getAccountPathIdentifier();

        boolean isWell;

        switch (requestMethod){
            case "GET":{
                isWell = containsBankId(servletPath, accountPathIdentifier, allAccountId);
                break;
            }
            case "DELETE":{
                isWell = containsBankId(servletPath, accountPathIdentifier, allAccountId);
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

    abstract Set<Long> getAllAccountId();

    abstract String getAccountPathIdentifier();

    private boolean containsBankId(String servletPath, String bankPathIdentifier, Set<Long> bankIdSet){
        int lastIndexOfSlash = servletPath.lastIndexOf(bankPathIdentifier);
        if(lastIndexOfSlash == -1){
            return false;
        }
        lastIndexOfSlash = lastIndexOfSlash + bankPathIdentifier.length();

        String possibleBankId = servletPath.substring(lastIndexOfSlash, servletPath.length());

        if(!possibleBankId.matches("\\d+")){
            return false;
        }

        return bankIdSet.contains(Long.parseLong(possibleBankId));
    }
}
