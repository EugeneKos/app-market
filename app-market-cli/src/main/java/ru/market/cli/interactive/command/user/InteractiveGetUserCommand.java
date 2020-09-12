package ru.market.cli.interactive.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.user.UserDTO;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveGetUserCommand implements Command {
    private UserRestClient userRestClient;
    private Printer printer;

    @Autowired
    public InteractiveGetUserCommand(UserRestClient userRestClient, Printer printer) {
        this.userRestClient = userRestClient;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Получить информацию о пользователе";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        UserDTO user = userRestClient.getCurrent();
        printer.printTable(PrinterUtils.createUsersTableToPrint(Collections.singletonList(user)));
        menu.back(reader);
    }
}
