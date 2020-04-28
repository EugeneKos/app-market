package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;

import ru.market.data.session.api.SessionDataManager;
import ru.market.dto.person.PersonDTO;
import ru.market.utils.JSONObjectUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@UrlFilter(urlPatterns = "/person",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/person", methods = ExcludeRequestMethod.Method.GET)}
)
public class PersonRequestFilter implements AuthFilter {
    private SessionDataManager sessionDataManager;

    public PersonRequestFilter(SessionDataManager sessionDataManager) {
        this.sessionDataManager = sessionDataManager;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Long personId = sessionDataManager.getUserData().getPersonId();

        String requestMethod = request.getMethod();

        boolean isWell = false;

        if("POST".equals(requestMethod)){
            isWell = isEqualPersonId(request.getInputStream(), personId);
        }

        if(isWell){
            authChain.doFilter(request, response, filterChain);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }

    private boolean isEqualPersonId(InputStream inputStream, Long personId){
        PersonDTO person = JSONObjectUtil.getJsonObjectFromInputStream(inputStream, PersonDTO.class);
        if(person == null){
            return false;
        }

        boolean isEqual = personId.equals(person.getId());
        if(isEqual){
            sessionDataManager.setCurrentRequestBody(person);
        }

        return isEqual;
    }
}
