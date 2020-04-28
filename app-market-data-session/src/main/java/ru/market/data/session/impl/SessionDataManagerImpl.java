package ru.market.data.session.impl;

import ru.market.data.session.api.SessionDataManager;

public class SessionDataManagerImpl implements SessionDataManager {
    @Override
    public void setSecretKey(String secretKey) {
        SessionContext.setSessionAttribute(SessionAttributeNames.SECRET_KEY, secretKey);
    }

    @Override
    public String getSecretKey() {
        return SessionContext.getSessionAttribute(SessionAttributeNames.SECRET_KEY);
    }
}
