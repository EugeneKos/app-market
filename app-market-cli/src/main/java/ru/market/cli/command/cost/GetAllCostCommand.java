package ru.market.cli.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.CostRestClient;
import ru.market.dto.cost.CostDTO;

import java.util.Map;
import java.util.Set;

import static ru.market.cli.command.CommandArguments.COST_LIMIT_ID_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_DATE_ARG;

@Service
public class GetAllCostCommand implements Command {
    private CostRestClient costRestClient;

    @Autowired
    public GetAllCostCommand(CostRestClient costRestClient) {
        this.costRestClient = costRestClient;
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
        Printer.print(costs);
    }
}
