package ru.market.web.session;

import javax.servlet.http.HttpSession;

public final class SessionContextHolder {
    private static ThreadLocal<HttpSession> threadLocalCurrentSession = new ThreadLocal<>();

    private SessionContextHolder(){}

    public static void setCurrentSession(HttpSession session){
        threadLocalCurrentSession.set(session);
    }

    public static HttpSession getCurrentSession(){
        return threadLocalCurrentSession.get();
    }

    public static void removeCurrentSession(){
        threadLocalCurrentSession.remove();
    }
}
