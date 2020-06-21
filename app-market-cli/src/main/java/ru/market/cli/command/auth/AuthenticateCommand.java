package ru.market.cli.command.auth;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.Printer;
import ru.market.client.rest.AuthenticateRestClient;
import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.PASSWORD_ARG;
import static ru.market.cli.command.CommandArguments.USERNAME_ARG;

public class AuthenticateCommand implements Command {
    private AuthenticateRestClient authenticateRestClient;
    private Printer printer;

    public AuthenticateCommand(AuthenticateRestClient authenticateRestClient, Printer printer) {
        this.authenticateRestClient = authenticateRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "auth";
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
        printer.print(result);
    }
}
