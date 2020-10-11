package ru.market.cli.interactive.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
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
    public String id() {
        return IDElement.DELETE_USER_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        userRestClient.deleteCurrent();
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
