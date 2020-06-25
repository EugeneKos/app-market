package ru.market.cli.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.command.CommandUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.user.UserDTO;

import java.util.Collections;
import java.util.Map;

@Service
public class GetUserCommand implements Command {
    private UserRestClient userRestClient;
    private Printer printer;

    @Autowired
    public GetUserCommand(UserRestClient userRestClient, Printer printer) {
        this.userRestClient = userRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "user-get";
    }

    @Override
    public String getDescription() {
        return "Команда на получение расширенной информации о пользователе";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        UserDTO user = userRestClient.getCurrent();
        printer.printTable(CommandUtils.createUsersTableToPrint(Collections.singletonList(user)));
    }
}
