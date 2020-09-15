package ru.market.cli.interactive.command.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.UserRestClient;
import ru.market.dto.user.UserDTO;
import ru.market.dto.user.UserUsernameDTO;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveChangeUsernameUserCommand extends InteractiveCommonCommand {
    private UserRestClient userRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveChangeUsernameUserCommand(UserRestClient userRestClient, CommandHelper commandHelper, Printer printer) {
        this.userRestClient = userRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Изменить имя пользователя";
    }

    @Override
    public void perform(BufferedReader reader) {
        UserUsernameDTO userUsernameDTO = UserUsernameDTO.builder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите новое имя пользователя",
                                true, UserUsernameDTO::setUsername
                        )
                ),
                userUsernameDTO
        );

        if(isInterrupted){
            return;
        }

        UserDTO user = userRestClient.changeUsername(userUsernameDTO);
        printer.printTable(PrinterUtils.createUsersTableToPrint(Collections.singletonList(user)));
    }
}
