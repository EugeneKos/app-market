package ru.market.cli.interactive.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.interactive.helper.command.CommandArgumentWrapper;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.client.rest.MoneyAccountRestClient;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveDeleteMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;
    private CommandHelper commandHelper;

    @Autowired
    public InteractiveDeleteMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, CommandHelper commandHelper) {
        this.moneyAccountRestClient = moneyAccountRestClient;
        this.commandHelper = commandHelper;
    }

    @Override
    public String name() {
        return "Удалить денежный счет";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        CommandArgumentWrapper commandArgumentWrapper = new CommandArgumentWrapper();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите id денежного счета", true,
                                (object, param) -> object.addCommandArgument("idMoneyForDelete", param)
                        )
                ),
                commandArgumentWrapper
        );

        if(isInterrupted){
            menu.back(reader);
            return;
        }

        moneyAccountRestClient.deleteById(
                Long.parseLong(commandArgumentWrapper.getCommandArgument("idMoneyForDelete"))
        );
        menu.back(reader);
    }
}
