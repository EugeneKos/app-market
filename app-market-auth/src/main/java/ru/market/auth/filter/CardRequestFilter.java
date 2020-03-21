package ru.market.auth.filter;

import ru.market.auth.annotation.ExcludeRequestMethod;
import ru.market.auth.annotation.UrlFilter;
import ru.market.auth.api.AuthFilterChain;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.domain.service.ICardService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Set;

@UrlFilter(urlPatterns = "/card*",
        excludeRequestMethods = {@ExcludeRequestMethod(url = "/card", methods = ExcludeRequestMethod.Method.PUT),
                                @ExcludeRequestMethod(url = "/card", methods = ExcludeRequestMethod.Method.GET)}
)
public class CardRequestFilter implements AuthFilter {
    private PersonDataManagement personDataManagement;
    private ICardService cardService;

    public CardRequestFilter(PersonDataManagement personDataManagement, ICardService cardService) {
        this.personDataManagement = personDataManagement;
        this.cardService = cardService;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         AuthFilterChain authChain, FilterChain filterChain) throws IOException, ServletException {

        Long personId = personDataManagement.getPerson().getId();
        Set<Long> allCardIdByPersonId = cardService.getAllCardIdByPersonId(personId);

        String servletPath = request.getServletPath();
        String requestMethod = request.getMethod();

        boolean isWell;

        switch (requestMethod){
            case "GET":{
                isWell = containsCardId(servletPath, allCardIdByPersonId);
                break;
            }
            case "DELETE":{
                isWell = containsCardId(servletPath, allCardIdByPersonId);
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

    private boolean containsCardId(String servletPath, Set<Long> cardIdSet){
        int lastIndexOfSlash = servletPath.lastIndexOf("card/");
        if(lastIndexOfSlash == -1){
            return false;
        }
        lastIndexOfSlash = lastIndexOfSlash + 5;

        String possibleCardId = servletPath.substring(lastIndexOfSlash, servletPath.length());

        if(!possibleCardId.matches("\\d+")){
            return false;
        }

        return cardIdSet.contains(Long.parseLong(possibleCardId));
    }
}
