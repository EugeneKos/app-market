package ru.market.data.session.impl;

import ru.market.data.session.api.SessionDataManager;
import ru.market.data.session.api.UserData;

public class SessionDataManagerImpl implements SessionDataManager {
    @Override
    public void setUserData(UserData userData) {
        SessionContext.setSessionAttribute(SessionAttributeNames.USER_DATA, userData);
    }

    @Override
    public UserData getUserData() {
        return SessionContext.getSessionAttribute(SessionAttributeNames.USER_DATA);
    }


    @Override
    public <T> void setCurrentRequestBody(T body) {
        SessionContext.setSessionAttribute(SessionAttributeNames.REQUEST_BODY, body);
    }

    @Override
    public <T> T getCurrentRequestBody() {
        T body = SessionContext.getSessionAttribute(SessionAttributeNames.REQUEST_BODY);
        SessionContext.removeSessionAttribute(SessionAttributeNames.REQUEST_BODY);
        return body;
    }
}
