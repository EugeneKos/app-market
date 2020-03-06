package ru.market.web.http.filter;

import ru.market.web.session.SessionContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class SessionFilter extends HttpFilter {
    private static final long serialVersionUID = -1161049175209399394L;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        SessionContextHolder.setCurrentSession(request.getSession());

        chain.doFilter(request, response);
    }
}
