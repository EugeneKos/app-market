package ru.market.cli.interactive.command.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
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
    public String name() {
        return "Завершить сеанс";
    }

    @Override
    public void perform(BufferedReader reader) {
        authenticateRestClient.logout();
    }
}
