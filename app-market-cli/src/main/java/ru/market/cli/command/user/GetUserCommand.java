package ru.market.cli.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.user.UserDTO;

import java.util.Map;

@Service
public class GetUserCommand implements Command {
    private UserRestClient userRestClient;

    @Autowired
    public GetUserCommand(UserRestClient userRestClient) {
        this.userRestClient = userRestClient;
    }

    @Override
    public String getName() {
        return "user-get";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        UserDTO userDTO = userRestClient.getCurrent();
        Printer.print(userDTO);
    }
}
