package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;

import ru.market.data.session.api.PersonDataManagement;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                //fixme: Необходимо удалять пользователя из сессии
                //isWell = isEqualPersonId(servletPath, personId);
                isWell = false;
                break;
            }
            case "POST":{
                //todo: Нужно брать тело запроса json и преобразовывать в класс
                isWell = false;
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
}
