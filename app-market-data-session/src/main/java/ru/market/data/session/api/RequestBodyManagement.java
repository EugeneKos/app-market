package ru.market.data.session.api;

public interface RequestBodyManagement {
    <T> void setCurrentRequestBody(T body);
    <T> T getCurrentRequestBody();
}
