package ru.market.cli.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.command.CommandUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.CostRestClient;
import ru.market.dto.cost.CostDTO;

import java.util.Map;
import java.util.Set;

import static ru.market.cli.command.CommandArguments.COST_LIMIT_ID_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_DATE_ARG;

@Service
public class GetAllCostCommand implements Command {
    private CostRestClient costRestClient;
    private Printer printer;

    @Autowired
    public GetAllCostCommand(CostRestClient costRestClient, Printer printer) {
        this.costRestClient = costRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "cost-get-all";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{COST_LIMIT_ID_ARG, MANDATORY_DATE_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        Set<CostDTO> costs = costRestClient.getAllByCostLimitIdAndDate(
                Long.parseLong(arguments.get(COST_LIMIT_ID_ARG)), arguments.get(MANDATORY_DATE_ARG)
        );
        printer.printTable(CommandUtils.createCostsTableToPrint(costs));
    }
}
