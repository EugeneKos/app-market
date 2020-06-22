package ru.market.cli.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.CostLimitRestClient;
import ru.market.dto.limit.CostLimitInfoDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.ID_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_DATE_ARG;

@Service
public class GetCostLimitInfoCommand implements Command {
    private CostLimitRestClient costLimitRestClient;

    @Autowired
    public GetCostLimitInfoCommand(CostLimitRestClient costLimitRestClient) {
        this.costLimitRestClient = costLimitRestClient;
    }

    @Override
    public String getName() {
        return "cost-limit-get-info";
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
        Printer.print(costLimitInfo);
    }
}
