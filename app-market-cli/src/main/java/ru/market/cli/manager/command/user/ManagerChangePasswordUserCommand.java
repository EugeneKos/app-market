package ru.market.cli.manager.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.result.ResultDTO;
import ru.market.dto.user.UserPasswordDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.NEW_PASSWORD_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.OLD_PASSWORD_ARG;

@Service
public class ManagerChangePasswordUserCommand implements Command {
    private UserRestClient userRestClient;
    private Printer printer;

    @Autowired
    public ManagerChangePasswordUserCommand(UserRestClient userRestClient, Printer printer) {
        this.userRestClient = userRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "user-ch-password";
    }

    @Override
    public String getDescription() {
        return "Команда на изменение пароля пользователя";
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
        printer.printTable(PrinterUtils.createResultsTableToPrint(Collections.singletonList(result)));
    }
}