package ru.market.auth.filter;

import ru.market.auth.api.AuthFilterChain;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Set;

public abstract class BankRequestFilter implements AuthFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Set<Long> allBankId = getAllBankId();

        String servletPath = request.getServletPath();
        String requestMethod = request.getMethod();
        String bankPathIdentifier = getBankPathIdentifier();

        boolean isWell;

        switch (requestMethod){
            case "GET":{
                isWell = containsBankId(servletPath, bankPathIdentifier, allBankId);
                break;
            }
            case "DELETE":{
                isWell = containsBankId(servletPath, bankPathIdentifier, allBankId);
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

    abstract Set<Long> getAllBankId();

    abstract String getBankPathIdentifier();

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
