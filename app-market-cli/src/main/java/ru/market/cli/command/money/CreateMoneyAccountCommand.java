package ru.market.cli.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.money.MoneyAccountNoIdDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.BALANCE_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_DESCRIPTION_ARG;
import static ru.market.cli.command.CommandArguments.NAME_ARG;

@Service
public class CreateMoneyAccountCommand implements Command {
    private MoneyAccountRestClient moneyAccountRestClient;

    @Autowired
    public CreateMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient) {
        this.moneyAccountRestClient = moneyAccountRestClient;
    }

    @Override
    public String getName() {
        return "money-create";
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

        MoneyAccountDTO moneyAccountDTO = moneyAccountRestClient.create(moneyAccountNoIdDTO);
        Printer.print(moneyAccountDTO);
    }
}
