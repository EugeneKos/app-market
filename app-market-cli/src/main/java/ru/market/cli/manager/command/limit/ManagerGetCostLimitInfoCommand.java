package ru.market.cli.manager.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.CostLimitRestClient;
import ru.market.dto.limit.CostLimitInfoDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.ID_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.MANDATORY_DATE_ARG;

@Service
public class ManagerGetCostLimitInfoCommand implements Command {
    private CostLimitRestClient costLimitRestClient;
    private Printer printer;

    @Autowired
    public ManagerGetCostLimitInfoCommand(CostLimitRestClient costLimitRestClient, Printer printer) {
        this.costLimitRestClient = costLimitRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "cost-limit-get-info";
    }

    @Override
    public String getDescription() {
        return "Команда на получение расширенной информации по лимиту";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{ID_ARG, MANDATORY_DATE_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        CostLimitInfoDTO costLimitInfo = costLimitRestClient.getCostLimitInfoById(
                Long.parseLong(arguments.get(ID_ARG)), arguments.get(MANDATORY_DATE_ARG)
        );
        printer.printTable(PrinterUtils.createCostLimitInfosTableToPrint(Collections.singletonList(costLimitInfo)));
    }
}
