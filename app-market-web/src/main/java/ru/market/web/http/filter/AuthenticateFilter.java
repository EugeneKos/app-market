package ru.market.web.http.filter;

import ru.market.web.auth.AuthenticateService;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/mi"})
public class AuthenticateFilter extends HttpFilter {
    private static final long serialVersionUID = -7476424079788176274L;

    private AuthenticateService authenticateService;

    @Override
    public void init(FilterConfig config) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(config.getServletContext());

        authenticateService = webApplicationContext.getBean(AuthenticateService.class);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if(authenticateService.isAuthenticate()){
            chain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}
