package ru.market.cli.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserUsernameDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.USERNAME_ARG;

@Service
public class ChangeUsernameUserCommand implements Command {
    private UserRestClient userRestClient;

    @Autowired
    public ChangeUsernameUserCommand(UserRestClient userRestClient) {
        this.userRestClient = userRestClient;
    }

    @Override
    public String getName() {
        return "user-ch-username";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{USERNAME_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        UserUsernameDTO userUsernameDTO = UserUsernameDTO.builder()
                .username(arguments.get(USERNAME_ARG))
                .build();

        UserDTO userDTO = userRestClient.changeUsername(userUsernameDTO);
        Printer.print(userDTO);
    }
}
