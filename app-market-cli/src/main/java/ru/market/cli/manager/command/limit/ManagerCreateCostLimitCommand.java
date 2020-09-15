package ru.market.cli.manager.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.printer.PrinterUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.CostLimitRestClient;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.BEGIN_DATE_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.MANDATORY_DESCRIPTION_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.END_DATE_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.SUM_ARG;

@Service
public class ManagerCreateCostLimitCommand implements Command {
    private CostLimitRestClient costLimitRestClient;
    private Printer printer;

    @Autowired
    public ManagerCreateCostLimitCommand(CostLimitRestClient costLimitRestClient, Printer printer) {
        this.costLimitRestClient = costLimitRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "cost-limit-create";
    }

    @Override
    public String getDescription() {
        return "Команда на создание лимита";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{SUM_ARG, MANDATORY_DESCRIPTION_ARG, BEGIN_DATE_ARG, END_DATE_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        CostLimitNoIdDTO costLimitNoIdDTO = CostLimitNoIdDTO.builder()
                .sum(arguments.get(SUM_ARG))
                .description(arguments.get(MANDATORY_DESCRIPTION_ARG))
                .beginDateStr(arguments.get(BEGIN_DATE_ARG))
                .endDateStr(arguments.get(END_DATE_ARG))
                .build();

        CostLimitDTO costLimitDTO = costLimitRestClient.create(costLimitNoIdDTO);
        printer.printTable(PrinterUtils.createCostLimitsTableToPrint(Collections.singletonList(costLimitDTO)));
    }
}
