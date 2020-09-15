package ru.market.cli.manager.command.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.AuthenticateRestClient;
import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.PASSWORD_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.USERNAME_ARG;

@Service
public class ManagerAuthenticateCommand implements Command {
    private AuthenticateRestClient authenticateRestClient;
    private Printer printer;

    @Autowired
    public ManagerAuthenticateCommand(AuthenticateRestClient authenticateRestClient, Printer printer) {
        this.authenticateRestClient = authenticateRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "auth";
    }

    @Override
    public String getDescription() {
        return "Команда аутентификации пользователя";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{USERNAME_ARG, PASSWORD_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        UsernamePasswordDTO usernamePasswordDTO = UsernamePasswordDTO.builder()
                .username(arguments.get(USERNAME_ARG))
                .password(arguments.get(PASSWORD_ARG))
                .build();

        ResultDTO result = authenticateRestClient.authenticate(usernamePasswordDTO);
        printer.printTable(PrinterUtils.createResultsTableToPrint(Collections.singletonList(result)));
    }
}
