package ru.market.auth.impl;

import ru.market.auth.api.AuthFilterChain;
import ru.market.auth.api.AuthFilterHandler;
import ru.market.auth.filter.AuthFilter;

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
    private AuthFilterHandler authFilterHandler;

    private List<AuthFilter> filters = new ArrayList<>();

    private Iterator<AuthFilter> iterator;

    public AuthFilterChainImpl(AuthFilterHandler authFilterHandler) {
        this.authFilterHandler = authFilterHandler;
    }

    @PostConstruct
    private void initialize(){
        iterator = filters.iterator();
    }

    public void registerFilters(List<AuthFilter> filters){
        this.filters = new ArrayList<>(filters);
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
