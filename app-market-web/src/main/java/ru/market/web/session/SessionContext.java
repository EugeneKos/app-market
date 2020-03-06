package ru.market.web.session;

import ru.market.dto.person.PersonDTO;

import javax.servlet.http.HttpSession;

public final class SessionContext {
    private SessionContext(){}

    public static void setPerson(PersonDTO person){
        HttpSession currentSession = SessionContextHolder.getCurrentSession();
        currentSession.setAttribute(SessionAttributeNames.PERSON.name(), person);
    }

    public static PersonDTO getPerson(){
        HttpSession currentSession = SessionContextHolder.getCurrentSession();
        return (PersonDTO) currentSession.getAttribute(SessionAttributeNames.PERSON.name());
    }
}
