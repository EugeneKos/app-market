package ru.market.data.session.impl;

import javax.servlet.http.HttpSession;

final class SessionContext {
    private SessionContext(){}

    static <T> void setSessionAttribute(SessionAttributeNames attribute, T object){
        HttpSession currentSession = SessionContextHolder.getCurrentSession();
        currentSession.setAttribute(attribute.name(), object);
    }

    @SuppressWarnings("unchecked")
    static <T> T getSessionAttribute(SessionAttributeNames attribute){
        HttpSession currentSession = SessionContextHolder.getCurrentSession();
        return (T) currentSession.getAttribute(attribute.name());
    }

    static void removeSessionAttribute(SessionAttributeNames attribute){
        HttpSession currentSession = SessionContextHolder.getCurrentSession();
        currentSession.removeAttribute(attribute.name());
    }

    static void setCurrentSession(HttpSession session){
        SessionContextHolder.setCurrentSession(session);
    }

    static void removeCurrentSession(){
        SessionContextHolder.removeCurrentSession();
    }

    static void invalidateSession(){
        HttpSession currentSession = SessionContextHolder.getCurrentSession();
        currentSession.invalidate();
    }

    static void setMaxInactiveInterval(int interval){
        HttpSession currentSession = SessionContextHolder.getCurrentSession();
        currentSession.setMaxInactiveInterval(interval);
    }
}
