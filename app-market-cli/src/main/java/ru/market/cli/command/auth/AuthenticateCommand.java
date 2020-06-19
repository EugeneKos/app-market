package ru.market.cli.command.auth;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.Printer;
import ru.market.client.rest.AuthenticateRestClient;
import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.result.ResultDTO;

import java.util.Map;

public class AuthenticateCommand implements Command {
    private static final Argument USERNAME_ARG = new Argument("-u", "--username", true);
    private static final Argument PASSWORD_ARG = new Argument("-p", "--password", true);

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
        UsernamePasswordDTO usernamePasswordDTO = new UsernamePasswordDTO();
        usernamePasswordDTO.setUsername(arguments.get(USERNAME_ARG));
        usernamePasswordDTO.setPassword(arguments.get(PASSWORD_ARG));

        ResultDTO result = authenticateRestClient.authenticate(usernamePasswordDTO);
        printer.print(result);
    }
}
