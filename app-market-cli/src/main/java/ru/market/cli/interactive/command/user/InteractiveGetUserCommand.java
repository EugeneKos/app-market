package ru.market.cli.interactive.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.user.UserDTO;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveGetUserCommand extends InteractiveCommonCommand {
    private UserRestClient userRestClient;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveGetUserCommand(UserRestClient userRestClient, CommandContext commandContext, Printer printer) {
        this.userRestClient = userRestClient;
        this.commandContext = commandContext;
        this.printer = printer;
    }

    @Override
    public String id() {
        return IDElement.GET_USER_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        UserDTO user = userRestClient.getCurrent();
        printer.printTable(PrinterUtils.createUsersTableToPrint(Collections.singletonList(user)));
        commandContext.putCommandArgument(CommandArgument.USER_ID, user.getId());
        return IDElement.USER_CONTROL_MENU;
    }

    @Override
    public String getElementIdByException() {
        return IDElement.APPLICATION_MENU;
    }

    @Override
    public String getElementIdByRestClientException() {
        return IDElement.APPLICATION_MENU;
    }
}
