package ru.market.data.session.impl;

import ru.market.data.session.api.RequestBodyManagement;

public class RequestBodyManagementImpl implements RequestBodyManagement {
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
