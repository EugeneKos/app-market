package ru.market.data.session.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

final class SessionContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionContext.class);

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
        LOGGER.info("Инвалидация сессии. Session id = {}", currentSession.getId());
        currentSession.invalidate();
    }

    static void setMaxInactiveInterval(int interval){
        HttpSession currentSession = SessionContextHolder.getCurrentSession();
        LOGGER.info("Установка временного интервала активности сессии. Session id = {}, interval = {}",
                currentSession.getId(), interval
        );
        currentSession.setMaxInactiveInterval(interval);
    }
}
