package ru.market.cli.interactive.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveGetMoneyAccountCommand extends InteractiveCommonCommand {
    private MoneyAccountRestClient moneyAccountRestClient;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveGetMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient,
                                             CommandContext commandContext,
                                             Printer printer) {

        this.moneyAccountRestClient = moneyAccountRestClient;
        this.commandContext = commandContext;
        this.printer = printer;
    }

    @Override
    public String id() {
        return IDElement.GET_MONEY_ACCOUNT_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        Long moneyAccountId = commandContext.getCommandArgument(CommandArgument.MONEY_ACCOUNT_ID);

        MoneyAccountDTO moneyAccount = moneyAccountRestClient.getById(moneyAccountId);
        printer.printTable(PrinterUtils.createMoneyAccountsTableToPrint(Collections.singletonList(moneyAccount)));

        return IDElement.MONEY_ACCOUNT_OPERATION_MENU;
    }

    @Override
    public String getElementIdByException() {
        return IDElement.MONEY_ACCOUNT_CONTROL_MENU;
    }

    @Override
    public String getElementIdByRestClientException() {
        return IDElement.MONEY_ACCOUNT_CONTROL_MENU;
    }
}
