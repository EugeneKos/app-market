package ru.market.cli.manager.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.BALANCE_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.MANDATORY_DESCRIPTION_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.NAME_ARG;

@Service
public class ManagerCreateMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;
    private Printer printer;

    @Autowired
    public ManagerCreateMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, Printer printer) {
        this.moneyAccountRestClient = moneyAccountRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "money-create";
    }

    @Override
    public String getDescription() {
        return "Команда на создание денежного счета";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{BALANCE_ARG, NAME_ARG, MANDATORY_DESCRIPTION_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        MoneyAccountNoIdDTO moneyAccountNoIdDTO = MoneyAccountNoIdDTO.builder()
                .balance(arguments.get(BALANCE_ARG))
                .name(arguments.get(NAME_ARG))
                .description(arguments.get(MANDATORY_DESCRIPTION_ARG))
                .build();

        MoneyAccountDTO moneyAccount = moneyAccountRestClient.create(moneyAccountNoIdDTO);
        printer.printTable(PrinterUtils.createMoneyAccountsTableToPrint(Collections.singletonList(moneyAccount)));
    }
}
