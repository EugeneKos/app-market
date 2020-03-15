package ru.market.auth.impl;

import ru.market.auth.api.AuthFilterChain;
import ru.market.auth.api.AuthenticateService;
import ru.market.auth.api.AuthFilterHandler;
import ru.market.auth.filter.AuthFilter;
import ru.market.auth.filter.AuthenticateFilter;
import ru.market.auth.filter.PersonRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AuthFilterChainImpl implements AuthFilterChain {
    private AuthenticateService authenticateService;
    private AuthFilterHandler authFilterHandler;

    private List<AuthFilter> filters;

    private Iterator<AuthFilter> iterator;

    public AuthFilterChainImpl(AuthenticateService authenticateService, AuthFilterHandler authFilterHandler) {
        this.authenticateService = authenticateService;
        this.authFilterHandler = authFilterHandler;
    }

    @PostConstruct
    private void init(){
        // todo: Было бы неплохо придумать скан фильтров
        filters = new ArrayList<>();
        filters.add(new PersonRequestFilter());
        filters.add(new AuthenticateFilter(authenticateService));
    }

    @Override
    public void initIterator() {
        // fixme: Надо избавиться от этого метода
        iterator = filters.iterator();
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        if(iterator.hasNext()){
            AuthFilter authFilter = iterator.next();

            if(authFilterHandler.isFilterMatch(authFilter, request)){
                authFilter.doFilter(request, response, this, filterChain);
            } else {
                doFilter(request, response, filterChain);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
