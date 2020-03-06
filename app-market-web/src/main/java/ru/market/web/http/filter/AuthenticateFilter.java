package ru.market.web.http.filter;

import ru.market.web.session.SessionContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/mi"})
public class AuthenticateFilter extends HttpFilter {
    private static final long serialVersionUID = -7476424079788176274L;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if(isAuthenticate()){
            chain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }

    private boolean isAuthenticate() {
        return SessionContext.getPerson() == null;
    }
}
