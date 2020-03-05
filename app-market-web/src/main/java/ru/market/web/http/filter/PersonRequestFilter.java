package ru.market.web.http.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/person/*"})
public class PersonRequestFilter extends HttpFilter {
    private static final long serialVersionUID = -7476424079788176274L;

    private static final String ALLOWABLE_METHOD = "PUT";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if(ALLOWABLE_METHOD.equalsIgnoreCase(request.getMethod())){
            chain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }
}
