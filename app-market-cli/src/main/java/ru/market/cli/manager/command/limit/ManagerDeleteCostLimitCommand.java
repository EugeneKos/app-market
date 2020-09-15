package ru.market.cli.manager.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.client.rest.CostLimitRestClient;

import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.COST_LIMIT_ROLLBACK_OP_ARG;
import static ru.market.cli.manager.ManagerCommandArguments.ID_ARG;

@Service
public class ManagerDeleteCostLimitCommand implements Command {
    private CostLimitRestClient costLimitRestClient;

    @Autowired
    public ManagerDeleteCostLimitCommand(CostLimitRestClient costLimitRestClient) {
        this.costLimitRestClient = costLimitRestClient;
    }

    @Override
    public String getName() {
        return "cost-limit-delete";
    }

    @Override
    public String getDescription() {
        return "Команда на удаление лимита";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{ID_ARG, COST_LIMIT_ROLLBACK_OP_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        costLimitRestClient.deleteById(Long.parseLong(arguments.get(ID_ARG)),
                Boolean.parseBoolean(arguments.get(COST_LIMIT_ROLLBACK_OP_ARG)));
    }
}
