package ru.market.web.http.listener;

import ru.market.web.session.SessionContextHolder;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebListener
public class SessionContextListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest servletRequest = (HttpServletRequest) sre.getServletRequest();
        HttpSession session = servletRequest.getSession();
        System.out.println("set session in thread local. Session id: " + session.getId());
        SessionContextHolder.setCurrentSession(session);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        SessionContextHolder.removeCurrentSession();
    }
}
