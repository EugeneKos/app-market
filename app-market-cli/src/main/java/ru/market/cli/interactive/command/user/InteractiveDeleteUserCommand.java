package ru.market.cli.interactive.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.client.rest.UserRestClient;

import java.io.BufferedReader;

@Service
public class InteractiveDeleteUserCommand implements Command {
    private UserRestClient userRestClient;

    @Autowired
    public InteractiveDeleteUserCommand(UserRestClient userRestClient) {
        this.userRestClient = userRestClient;
    }

    @Override
    public String name() {
        return "Удалить текущего пользователя";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        userRestClient.deleteCurrent();
        menu.back(reader);
    }
}
