package ru.market.cli.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.command.CommandUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.CostRestClient;
import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.command.CommandArguments.CATEGORY_ARG;
import static ru.market.cli.command.CommandArguments.COST_LIMIT_ID_ARG;
import static ru.market.cli.command.CommandArguments.DATE_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_DESCRIPTION_ARG;
import static ru.market.cli.command.CommandArguments.MONEY_ACCOUNT_ID_ARG;
import static ru.market.cli.command.CommandArguments.SUM_ARG;
import static ru.market.cli.command.CommandArguments.TIME_ARG;

@Service
public class CreateCostCommand implements Command {
    private CostRestClient costRestClient;
    private Printer printer;

    @Autowired
    public CreateCostCommand(CostRestClient costRestClient, Printer printer) {
        this.costRestClient = costRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "cost-create";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{SUM_ARG, MANDATORY_DESCRIPTION_ARG, CATEGORY_ARG, DATE_ARG, TIME_ARG,
                COST_LIMIT_ID_ARG, MONEY_ACCOUNT_ID_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        CostNoIdDTO costNoIdDTO = CostNoIdDTO.builder()
                .sum(arguments.get(SUM_ARG))
                .description(arguments.get(MANDATORY_DESCRIPTION_ARG))
                .category(arguments.get(CATEGORY_ARG))
                .dateStr(arguments.get(DATE_ARG))
                .timeStr(arguments.get(TIME_ARG))
                .costLimitId(Long.parseLong(arguments.get(COST_LIMIT_ID_ARG)))
                .moneyAccountId(Long.parseLong(arguments.get(MONEY_ACCOUNT_ID_ARG)))
                .build();

        CostDTO costDTO = costRestClient.create(costNoIdDTO);
        printer.printTable(CommandUtils.createCostsTableToPrint(Collections.singletonList(costDTO)));
    }
}
