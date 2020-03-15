package ru.market.auth.filter;

import ru.market.auth.impl.AuthFilterChainImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthFilter {
    void doFilter(HttpServletRequest request, HttpServletResponse response,
                  AuthFilterChainImpl authChain, FilterChain filterChain) throws IOException, ServletException;
}
