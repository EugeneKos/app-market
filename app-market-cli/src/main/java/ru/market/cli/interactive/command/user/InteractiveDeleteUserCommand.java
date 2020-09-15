package ru.market.cli.interactive.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.client.rest.UserRestClient;

import java.io.BufferedReader;

@Service
public class InteractiveDeleteUserCommand extends InteractiveCommonCommand {
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
    public void perform(BufferedReader reader) {
        userRestClient.deleteCurrent();
    }
}
