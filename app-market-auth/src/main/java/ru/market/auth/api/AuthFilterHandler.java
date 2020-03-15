package ru.market.auth.api;

import ru.market.auth.filter.AuthFilter;

import javax.servlet.http.HttpServletRequest;

public interface AuthFilterHandler {
    boolean isFilterMatch(AuthFilter authFilter, HttpServletRequest request);
}
