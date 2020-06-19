package ru.market.cli.command.auth;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.client.rest.AuthenticateRestClient;

import java.util.Map;

public class LogoutCommand implements Command {
    private AuthenticateRestClient authenticateRestClient;

    public LogoutCommand(AuthenticateRestClient authenticateRestClient) {
        this.authenticateRestClient = authenticateRestClient;
    }

    @Override
    public String getName() {
        return "logout";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> map) {
        authenticateRestClient.logout();
    }
}
