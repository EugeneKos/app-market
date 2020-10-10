package ru.market.cli.interactive.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommandUtils;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.interactive.helper.command.TypeWrapper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;

import java.io.BufferedReader;
import java.util.Set;

@Service
public class InteractiveGetAllMoneyAccountCommand extends InteractiveCommonCommand {
    private MoneyAccountRestClient moneyAccountRestClient;
    private CommandHelper commandHelper;
    private CommandContext commandContext;
    private Printer printer;

    @Autowired
    public InteractiveGetAllMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient,
                                                CommandHelper commandHelper,
                                                CommandContext commandContext,
                                                Printer printer) {

        this.moneyAccountRestClient = moneyAccountRestClient;
        this.commandHelper = commandHelper;
        this.commandContext = commandContext;
        this.printer = printer;
    }

    @Override
    public String id() {
        return IDElement.GET_ALL_MONEY_ACCOUNT_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        Set<MoneyAccountDTO> moneyAccounts = moneyAccountRestClient.getAll();
        printer.printTable(PrinterUtils.createMoneyAccountsTableToPrint(moneyAccounts));

        TypeWrapper<Long> typeWrapper = new TypeWrapper<>();

        boolean isInterrupted = InteractiveCommandUtils.fillMoneyAccountIdArgument(reader, commandHelper, typeWrapper);
        if(isInterrupted){
            return IDElement.MONEY_ACCOUNT_CONTROL_MENU;
        }

        commandContext.putCommandArgument(CommandArgument.MONEY_ACCOUNT_ID, typeWrapper.getTypeValue());

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
