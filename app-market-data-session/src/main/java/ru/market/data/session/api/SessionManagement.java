package ru.market.data.session.api;

import javax.servlet.http.HttpSession;

public interface SessionManagement {
    void setSession(HttpSession session);
    void removeSession();
    void invalidateSession();
    void setMaxInactiveInterval(int interval);
}
