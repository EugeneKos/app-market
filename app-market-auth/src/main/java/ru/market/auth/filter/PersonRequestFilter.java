package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.dto.person.PersonWithPasswordDTO;
import ru.market.utils.JSONObjectUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@UrlFilter(urlPatterns = "/person*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/person", methods = ExcludeRequestMethod.Method.PUT)}
)
public class PersonRequestFilter implements AuthFilter {
    private PersonDataManagement personDataManagement;

    public PersonRequestFilter(PersonDataManagement personDataManagement) {
        this.personDataManagement = personDataManagement;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Long personId = personDataManagement.getPerson().getId();

        String servletPath = request.getServletPath();
        String requestMethod = request.getMethod();

        boolean isWell;

        switch (requestMethod){
            case "GET":{
                isWell = isEqualPersonId(servletPath, personId);
                break;
            }
            case "DELETE":{
                isWell = isEqualPersonId(servletPath, personId);
                break;
            }
            case "POST":{
                isWell = isEqualPersonId(request.getInputStream(), personId);
                break;
            }
            default:
                isWell = false;
        }

        if(isWell){
            authChain.doFilter(request, response, filterChain);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }

    private boolean isEqualPersonId(String servletPath, Long personId){
        int lastIndexOfSlash = servletPath.lastIndexOf("person/");
        if(lastIndexOfSlash == -1){
            return false;
        }
        lastIndexOfSlash = lastIndexOfSlash + 7;

        String possiblePersonId = servletPath.substring(lastIndexOfSlash, servletPath.length());

        if(!possiblePersonId.matches("\\d+")){
            return false;
        }

        return personId.equals(Long.parseLong(possiblePersonId));
    }

    private boolean isEqualPersonId(InputStream inputStream, Long personId){
        PersonWithPasswordDTO person = JSONObjectUtil.getJsonObjectFromInputStream(inputStream, PersonWithPasswordDTO.class);
        if(person == null){
            return false;
        }
        return personId.equals(person.getId());
    }
}
