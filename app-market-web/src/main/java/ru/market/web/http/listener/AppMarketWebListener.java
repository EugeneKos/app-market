package ru.market.web.http.listener;

import ru.market.data.session.api.SessionManagement;
import ru.market.data.session.impl.SessionManagementImpl;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebListener
public class AppMarketWebListener implements ServletRequestListener {
    private SessionManagement sessionManagement = new SessionManagementImpl();

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        HttpServletRequest servletRequest = (HttpServletRequest) event.getServletRequest();
        HttpSession session = servletRequest.getSession();
        sessionManagement.setSession(session);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        sessionManagement.removeSession();
    }
}
