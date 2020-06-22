package ru.market.cli.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.result.ResultDTO;
import ru.market.dto.user.UserPasswordDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.NEW_PASSWORD_ARG;
import static ru.market.cli.command.CommandArguments.OLD_PASSWORD_ARG;

@Service
public class ChangePasswordUserCommand implements Command {
    private UserRestClient userRestClient;

    @Autowired
    public ChangePasswordUserCommand(UserRestClient userRestClient) {
        this.userRestClient = userRestClient;
    }

    @Override
    public String getName() {
        return "user-ch-password";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{OLD_PASSWORD_ARG, NEW_PASSWORD_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                .oldPassword(arguments.get(OLD_PASSWORD_ARG))
                .newPassword(arguments.get(NEW_PASSWORD_ARG))
                .build();

        ResultDTO result = userRestClient.changePassword(userPasswordDTO);
        Printer.print(result);
    }
}
