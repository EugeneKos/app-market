package ru.market.cli.manager.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.client.rest.CostRestClient;

import java.util.Map;

import static ru.market.cli.manager.ManagerCommandArguments.ID_ARG;

@Service
public class ManagerDeleteCostCommand implements Command {
    private CostRestClient costRestClient;

    @Autowired
    public ManagerDeleteCostCommand(CostRestClient costRestClient) {
        this.costRestClient = costRestClient;
    }

    @Override
    public String getName() {
        return "cost-delete";
    }

    @Override
    public String getDescription() {
        return "Команда на удаление затраты";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{ID_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        costRestClient.deleteById(Long.parseLong(arguments.get(ID_ARG)));
    }
}
