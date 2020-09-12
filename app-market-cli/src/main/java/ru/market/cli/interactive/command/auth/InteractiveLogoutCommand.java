package ru.market.cli.interactive.command.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.client.rest.AuthenticateRestClient;

import java.io.BufferedReader;

@Service
public class InteractiveLogoutCommand implements Command {
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
    public void perform(BufferedReader reader, Menu menu) {
        authenticateRestClient.logout();
        menu.back(reader);
    }
}
