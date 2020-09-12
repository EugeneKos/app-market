package ru.market.cli.interactive.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.result.ResultDTO;
import ru.market.dto.user.UserPasswordDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class InteractiveChangePasswordUserCommand implements Command {
    private UserRestClient userRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveChangePasswordUserCommand(UserRestClient userRestClient, CommandHelper commandHelper, Printer printer) {
        this.userRestClient = userRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Изменить пароль пользователя";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите старый пароль", true, UserPasswordDTO::setOldPassword),
                        new CommandDetail<>("Введите новый пароль", true, UserPasswordDTO::setNewPassword)
                )),
                userPasswordDTO
        );

        if(isInterrupted){
            menu.back(reader);
            return;
        }

        ResultDTO result = userRestClient.changePassword(userPasswordDTO);
        printer.printTable(PrinterUtils.createResultsTableToPrint(Collections.singletonList(result)));

        menu.back(reader);
    }
}
