package ru.market.cli.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.CostLimitRestClient;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.BEGIN_DATE_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_DESCRIPTION_ARG;
import static ru.market.cli.command.CommandArguments.END_DATE_ARG;
import static ru.market.cli.command.CommandArguments.SUM_ARG;

@Service
public class CreateCostLimitCommand implements Command {
    private CostLimitRestClient costLimitRestClient;

    @Autowired
    public CreateCostLimitCommand(CostLimitRestClient costLimitRestClient) {
        this.costLimitRestClient = costLimitRestClient;
    }

    @Override
    public String getName() {
        return "cost-limit-create";
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
        Printer.print(costLimitDTO);
    }
}
