package ru.market.cli.interactive.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.CommandArgumentWrapper;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveGetMoneyAccountCommand extends InteractiveCommonCommand {
    private MoneyAccountRestClient moneyAccountRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveGetMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, CommandHelper commandHelper, Printer printer) {
        this.moneyAccountRestClient = moneyAccountRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Получить денежный счет";
    }

    @Override
    public void perform(BufferedReader reader) {
        CommandArgumentWrapper commandArgumentWrapper = new CommandArgumentWrapper();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите id денежного счета", true,
                                (object, param) -> object.addCommandArgument("idMoneyForGet", param)
                        )
                ),
                commandArgumentWrapper
        );

        if(isInterrupted){
            return;
        }

        MoneyAccountDTO moneyAccount = moneyAccountRestClient.getById(
                Long.parseLong(commandArgumentWrapper.getCommandArgument("idMoneyForGet"))
        );

        printer.printTable(PrinterUtils.createMoneyAccountsTableToPrint(Collections.singletonList(moneyAccount)));
    }
}
