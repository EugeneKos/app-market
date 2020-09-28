package ru.market.cli.interactive.command.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.AuthenticateRestClient;
import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;

import java.io.BufferedReader;
import java.io.Console;
import java.util.Collections;

@Service
public class InteractiveAuthenticateCommand extends InteractiveCommonCommand {
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
    public void perform(BufferedReader reader) {
        UsernamePasswordDTO usernamePasswordDTO = UsernamePasswordDTO.builder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите имя пользователя", true, UsernamePasswordDTO::setUsername)
                ),
                usernamePasswordDTO
        );

        if(isInterrupted){
            return;
        }

        usernamePasswordDTO.setPassword(readPassword());

        ResultDTO result = authenticateRestClient.authenticate(usernamePasswordDTO);
        printer.printTable(PrinterUtils.createResultsTableToPrint(Collections.singletonList(result)));
    }

    private String readPassword(){
        char [] password;

        do {
            System.out.print("Введите пароль: ");
            Console console = System.console();
            password = console.readPassword();
        } while (password == null || password.length == 0);

        return new String(password);
    }
}
