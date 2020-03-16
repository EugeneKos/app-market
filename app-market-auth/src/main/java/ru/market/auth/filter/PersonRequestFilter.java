package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.impl.AuthFilterChainImpl;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@UrlFilter(urlPatterns = "/person*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/person", methods = ExcludeRequestMethod.Method.PUT)}
)
public class PersonRequestFilter implements AuthFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChainImpl authChain, FilterChain filterChain) throws IOException {

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
    }
}
