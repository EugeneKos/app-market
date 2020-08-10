package ru.market.auth.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;
import ru.market.auth.api.AuthenticateService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@UrlFilter(urlPatterns = {
        "/user", "/change/username", "/change/password", "/person", "/money-account*", "/operation*",
        "/cost-limit*", "/cost*"
})
public class AuthenticateFilter implements AuthFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateFilter.class);

    private AuthenticateService authenticateService;

    public AuthenticateFilter(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        if(authenticateService.isAuthenticate()){
            authChain.doFilter(request, response, filterChain);
        } else {
            LOGGER.error("Ошибка аутентификации пользователя");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}
