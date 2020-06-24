package ru.market.cli.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.command.CommandUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.CostLimitRestClient;
import ru.market.dto.limit.CostLimitDTO;

import java.util.Map;
import java.util.Set;

@Service
public class GetAllCostLimitCommand implements Command {
    private CostLimitRestClient costLimitRestClient;
    private Printer printer;

    @Autowired
    public GetAllCostLimitCommand(CostLimitRestClient costLimitRestClient, Printer printer) {
        this.costLimitRestClient = costLimitRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "cost-limit-get-all";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[0];
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        Set<CostLimitDTO> costLimits = costLimitRestClient.getAll();
        printer.printTable(CommandUtils.createCostLimitsTableToPrint(costLimits));
    }
}
