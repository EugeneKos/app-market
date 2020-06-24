package ru.market.cli.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.command.CommandUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.CostRestClient;
import ru.market.dto.cost.CostDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.command.CommandArguments.CATEGORY_ARG;
import static ru.market.cli.command.CommandArguments.COST_LIMIT_ID_ARG;
import static ru.market.cli.command.CommandArguments.DATE_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_DESCRIPTION_ARG;
import static ru.market.cli.command.CommandArguments.ID_ARG;
import static ru.market.cli.command.CommandArguments.MONEY_ACCOUNT_ID_ARG;
import static ru.market.cli.command.CommandArguments.SUM_ARG;
import static ru.market.cli.command.CommandArguments.TIME_ARG;

@Service
public class UpdateCostCommand implements Command {
    private CostRestClient costRestClient;
    private Printer printer;

    @Autowired
    public UpdateCostCommand(CostRestClient costRestClient, Printer printer) {
        this.costRestClient = costRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "cost-update";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{ID_ARG, SUM_ARG, MANDATORY_DESCRIPTION_ARG, CATEGORY_ARG, DATE_ARG, TIME_ARG,
                COST_LIMIT_ID_ARG, MONEY_ACCOUNT_ID_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        CostDTO costDTO = CostDTO.costBuilder()
                .id(Long.parseLong(arguments.get(ID_ARG)))
                .build();

        costDTO.setSum(arguments.get(SUM_ARG));
        costDTO.setDescription(arguments.get(MANDATORY_DESCRIPTION_ARG));
        costDTO.setCategory(arguments.get(CATEGORY_ARG));
        costDTO.setDateStr(arguments.get(DATE_ARG));
        costDTO.setTimeStr(arguments.get(TIME_ARG));
        costDTO.setCostLimitId(Long.parseLong(arguments.get(COST_LIMIT_ID_ARG)));
        costDTO.setMoneyAccountId(Long.parseLong(arguments.get(MONEY_ACCOUNT_ID_ARG)));

        CostDTO updated = costRestClient.update(costDTO);
        printer.printTable(CommandUtils.createCostsTableToPrint(Collections.singletonList(updated)));
    }
}
