package ru.market.cli.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.client.rest.UserRestClient;

import java.util.Map;

@Service
public class DeleteUserCommand implements Command {
    private UserRestClient userRestClient;

    @Autowired
    public DeleteUserCommand(UserRestClient userRestClient) {
        this.userRestClient = userRestClient;
    }

    @Override
    public String getName() {
        return "user-delete";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        userRestClient.deleteCurrent();
    }
}