package ru.market.auth.filter;

import ru.market.auth.api.AuthenticateService;
import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.impl.AuthFilterChainImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@UrlFilter(urlPatterns = {"/mi"})
public class AuthenticateFilter implements AuthFilter {
    private AuthenticateService authenticateService;

    public AuthenticateFilter(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChainImpl authChain, FilterChain filterChain) throws IOException, ServletException {

        if(authenticateService.isAuthenticate()){
            authChain.doFilter(request, response, filterChain);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}