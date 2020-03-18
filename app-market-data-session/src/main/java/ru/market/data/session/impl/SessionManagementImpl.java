package ru.market.data.session.impl;

import ru.market.data.session.api.SessionManagement;

import javax.servlet.http.HttpSession;

public class SessionManagementImpl implements SessionManagement {
    @Override
    public void setSession(HttpSession session) {
        SessionContext.setCurrentSession(session);
    }

    @Override
    public void removeSession() {
        SessionContext.removeCurrentSession();
    }

    @Override
    public void invalidateSession() {
        SessionContext.invalidateSession();
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        SessionContext.setMaxInactiveInterval(interval);
    }
}
