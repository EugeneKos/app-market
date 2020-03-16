package ru.market.web.http.filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ru.market.auth.api.AuthFilterChain;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AppMarketWebFilter extends HttpFilter {
    private static final long serialVersionUID = 4422158001342317500L;

    private WebApplicationContext webApplicationContext;

    @Override
    public void init(FilterConfig config) {
        webApplicationContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(config.getServletContext());
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        AuthFilterChain authFilterChain = webApplicationContext.getBean(AuthFilterChain.class);
        authFilterChain.doFilter(request, response, chain);
    }
}
