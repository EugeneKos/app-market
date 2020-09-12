package ru.market.cli.interactive.command.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.AuthenticateRestClient;
import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class InteractiveAuthenticateCommand implements Command {
    private AuthenticateRestClient authenticateRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveAuthenticateCommand(AuthenticateRestClient authenticateRestClient, CommandHelper commandHelper, Printer printer) {
        this.authenticateRestClient = authenticateRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Аутентификация";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        UsernamePasswordDTO usernamePasswordDTO = UsernamePasswordDTO.builder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите имя пользователя", true, UsernamePasswordDTO::setUsername),
                        new CommandDetail<>("Введите пароль", true, UsernamePasswordDTO::setPassword)
                )),
                usernamePasswordDTO
        );

        if(isInterrupted){
            menu.back(reader);
            return;
        }

        ResultDTO result = authenticateRestClient.authenticate(usernamePasswordDTO);
        printer.printTable(PrinterUtils.createResultsTableToPrint(Collections.singletonList(result)));
        
        menu.back(reader);
    }
}
