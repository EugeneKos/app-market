package ru.market.cli.interactive.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
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
public class InteractiveChangePasswordUserCommand extends InteractiveCommonCommand {
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
    public String id() {
        return IDElement.CHANGE_PASSWORD_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
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
            return IDElement.USER_CONTROL_MENU;
        }

        ResultDTO result = userRestClient.changePassword(userPasswordDTO);
        printer.printTable(PrinterUtils.createResultsTableToPrint(Collections.singletonList(result)));

        return IDElement.MAIN_MENU;
    }

    @Override
    public String getElementIdByException() {
        return IDElement.MAIN_MENU;
    }

    @Override
    public String getElementIdByRestClientException() {
        return IDElement.MAIN_MENU;
    }
}
