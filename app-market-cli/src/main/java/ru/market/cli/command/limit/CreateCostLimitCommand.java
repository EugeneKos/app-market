package ru.market.cli.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.command.CommandUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.CostLimitRestClient;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.command.CommandArguments.BEGIN_DATE_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_DESCRIPTION_ARG;
import static ru.market.cli.command.CommandArguments.END_DATE_ARG;
import static ru.market.cli.command.CommandArguments.SUM_ARG;

@Service
public class CreateCostLimitCommand implements Command {
    private CostLimitRestClient costLimitRestClient;
    private Printer printer;

    @Autowired
    public CreateCostLimitCommand(CostLimitRestClient costLimitRestClient, Printer printer) {
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
        printer.printTable(CommandUtils.createCostLimitsTableToPrint(Collections.singletonList(costLimitDTO)));
    }
}
