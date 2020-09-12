package ru.market.cli.interactive.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.element.Command;
import ru.market.cli.interactive.element.Menu;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.printer.Printer;
import ru.market.cli.printer.PrinterUtils;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class InteractiveCreateMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;
    private CommandHelper commandHelper;
    private Printer printer;

    @Autowired
    public InteractiveCreateMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, CommandHelper commandHelper, Printer printer) {
        this.moneyAccountRestClient = moneyAccountRestClient;
        this.commandHelper = commandHelper;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "Создать денежный счет";
    }

    @Override
    public void perform(BufferedReader reader, Menu menu) {
        MoneyAccountNoIdDTO moneyAccountNoIdDTO = MoneyAccountNoIdDTO.builder().build();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите баланс", true, MoneyAccountNoIdDTO::setBalance),
                        new CommandDetail<>("Введите название", true, MoneyAccountNoIdDTO::setName),
                        new CommandDetail<>("Введите описание", true, MoneyAccountNoIdDTO::setDescription)
                )),
                moneyAccountNoIdDTO
        );

        if(isInterrupted){
            menu.back(reader);
            return;
        }

        MoneyAccountDTO moneyAccount = moneyAccountRestClient.create(moneyAccountNoIdDTO);
        printer.printTable(PrinterUtils.createMoneyAccountsTableToPrint(Collections.singletonList(moneyAccount)));
        menu.back(reader);
    }
}
