package ru.market.cli.interactive.command.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.client.rest.AuthenticateRestClient;

import java.io.BufferedReader;

@Service
public class InteractiveLogoutCommand extends InteractiveCommonCommand {
    private AuthenticateRestClient authenticateRestClient;

    @Autowired
    public InteractiveLogoutCommand(AuthenticateRestClient authenticateRestClient) {
        this.authenticateRestClient = authenticateRestClient;
    }

    @Override
    public String id() {
        return IDElement.LOGOUT_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        authenticateRestClient.logout();
        return IDElement.MAIN_MENU;
    }

    @Override
    public String getElementIdByException() {
        return IDElement.MAIN_MENU;
    }

    @Override
    public String getElementIdByRestClientException() {
        return IDElement.MAIN_MENU;
    }
}
