package ru.market.cli.command.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.client.rest.AuthenticateRestClient;

import java.util.Map;

@Service
public class LogoutCommand implements Command {
    private AuthenticateRestClient authenticateRestClient;

    @Autowired
    public LogoutCommand(AuthenticateRestClient authenticateRestClient) {
        this.authenticateRestClient = authenticateRestClient;
    }

    @Override
    public String getName() {
        return "logout";
    }

    @Override
    public String getDescription() {
        return "Команда выхода пользователя из системы";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        authenticateRestClient.logout();
    }
}
