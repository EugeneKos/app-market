package ru.market.cli.interactive.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommandUtils;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.TypeWrapper;
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
        TypeWrapper<Long> typeWrapper = new TypeWrapper<>();

        boolean isInterrupted = InteractiveCommandUtils.fillMoneyAccountIdArgument(reader, commandHelper, typeWrapper);

        if(isInterrupted){
            return;
        }

        MoneyAccountDTO moneyAccount = moneyAccountRestClient.getById(typeWrapper.getTypeValue());
        printer.printTable(PrinterUtils.createMoneyAccountsTableToPrint(Collections.singletonList(moneyAccount)));
    }
}
