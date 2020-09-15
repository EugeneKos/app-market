package ru.market.cli.manager.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.CostRestClient;
import ru.market.dto.cost.CostDTO;

import java.util.Map;
import java.util.Set;

import static ru.market.cli.manager.ManagerCommandArguments.COST_LIMIT_ID_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.MANDATORY_DATE_ARG;

@Service
public class ManagerGetAllCostByDateCommand implements Command {
    private CostRestClient costRestClient;
    private Printer printer;

    @Autowired
    public ManagerGetAllCostByDateCommand(CostRestClient costRestClient, Printer printer) {
        this.costRestClient = costRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "cost-get-all-by-date";
    }

    @Override
    public String getDescription() {
        return "Команда на получение всех затрат по дате";
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
        printer.printTable(PrinterUtils.createCostsTableToPrint(costs));
    }
}
