package ru.market.data.session.impl;

import javax.servlet.http.HttpSession;

final class SessionContextHolder {
    private static ThreadLocal<HttpSession> threadLocalCurrentSession = new ThreadLocal<>();

    private SessionContextHolder(){}

    static void setCurrentSession(HttpSession session){
        threadLocalCurrentSession.set(session);
    }

    static HttpSession getCurrentSession(){
        return threadLocalCurrentSession.get();
    }

    static void removeCurrentSession(){
        threadLocalCurrentSession.remove();
    }
}
