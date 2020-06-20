package ru.market.cli.command.money;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.Printer;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.BALANCE_ARG;
import static ru.market.cli.command.CommandArguments.DESCRIPTION_ARG;
import static ru.market.cli.command.CommandArguments.NAME_ARG;

public class CreateMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;
    private Printer printer;

    public CreateMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, Printer printer) {
        this.moneyAccountRestClient = moneyAccountRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "money-create";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{BALANCE_ARG, NAME_ARG, DESCRIPTION_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        MoneyAccountNoIdDTO moneyAccountNoIdDTO = new MoneyAccountNoIdDTO();
        moneyAccountNoIdDTO.setBalance(arguments.get(BALANCE_ARG));
        moneyAccountNoIdDTO.setName(arguments.get(NAME_ARG));
        moneyAccountNoIdDTO.setDescription(arguments.get(DESCRIPTION_ARG));

        MoneyAccountDTO moneyAccountDTO = moneyAccountRestClient.create(moneyAccountNoIdDTO);
        printer.print(moneyAccountDTO);
    }
}
