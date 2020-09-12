package ru.market.cli.manager.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserUsernameDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.USERNAME_ARG;

@Service
public class ManagerChangeUsernameUserCommand implements Command {
    private UserRestClient userRestClient;
    private Printer printer;

    @Autowired
    public ManagerChangeUsernameUserCommand(UserRestClient userRestClient, Printer printer) {
        this.userRestClient = userRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "user-ch-username";
    }

    @Override
    public String getDescription() {
        return "Команда на изменение логина пользователя";
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

        UserDTO user = userRestClient.changeUsername(userUsernameDTO);
        printer.printTable(PrinterUtils.createUsersTableToPrint(Collections.singletonList(user)));
    }
}
