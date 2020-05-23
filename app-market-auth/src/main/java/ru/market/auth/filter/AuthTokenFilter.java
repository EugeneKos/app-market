package ru.market.auth.filter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFields;
import ru.market.auth.api.AuthFilterChain;
import ru.market.data.session.api.SessionDataManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@UrlFilter(urlPatterns = {
        "/user", "/change/username", "/change/password", "/person", "/money-account*", "/operation*"
})
public class AuthTokenFilter implements AuthFilter {
    private SessionDataManager sessionDataManager;
    private PasswordEncoder passwordEncoder;

    public AuthTokenFilter(SessionDataManager sessionDataManager, PasswordEncoder passwordEncoder) {
        this.sessionDataManager = sessionDataManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        String authToken = request.getHeader(AuthFields.AUTH_TOKEN_HEADER);

        if(StringUtils.isEmpty(authToken)){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            return;
        }

        String secretKey = sessionDataManager.getUserData().getSecretKey();

        if(passwordEncoder.matches(secretKey, authToken)){
            authChain.doFilter(request, response, filterChain);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }
}
