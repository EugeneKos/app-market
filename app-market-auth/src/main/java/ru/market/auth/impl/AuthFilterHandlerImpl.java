package ru.market.auth.impl;

import ru.market.auth.api.AuthFilterHandler;
import ru.market.auth.filter.AuthFilter;
import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;

import javax.servlet.http.HttpServletRequest;

public class AuthFilterHandlerImpl implements AuthFilterHandler {
    @Override
    public boolean isFilterMatch(AuthFilter authFilter, HttpServletRequest request) {
        Class<? extends AuthFilter> authFilterClass = authFilter.getClass();

        UrlFilter urlFilter = authFilterClass.getAnnotation(UrlFilter.class);
        if(urlFilter == null){
            return false;
        }

        for (ExcludeRequestMethod excludeRequestMethod : urlFilter.excludeRequestMethods()){
            if(request.getServletPath().equals(excludeRequestMethod.url())){
                for (ExcludeRequestMethod.Method method : excludeRequestMethod.methods()){
                    if(request.getMethod().equals(method.name())){
                        return false;
                    }
                }
            }
        }

        for (String urlPattern : urlFilter.urlPatterns()){
            // fixme: Необходимо регулярное выражение
            if(request.getServletPath().equals(urlPattern)){
                return true;
            }
        }

        return false;
    }
}
