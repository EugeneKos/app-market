package ru.market.cli.interactive.command.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.interactive.helper.command.CommandArgumentWrapper;
import ru.market.client.rest.CostRestClient;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveDeleteCostCommand extends InteractiveCommonCommand {
    private CostRestClient costRestClient;
    private CommandHelper commandHelper;

    @Autowired
    public InteractiveDeleteCostCommand(CostRestClient costRestClient, CommandHelper commandHelper) {
        this.costRestClient = costRestClient;
        this.commandHelper = commandHelper;
    }

    @Override
    public String name() {
        return "Удалить затрату";
    }

    @Override
    public void perform(BufferedReader reader) {
        CommandArgumentWrapper commandArgumentWrapper = new CommandArgumentWrapper();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>(
                                "Введите id затраты",
                                true,
                                (object, param) -> object.addCommandArgument("idCost", param))
                ),
                commandArgumentWrapper
        );

        if(isInterrupted){
            return;
        }

        costRestClient.deleteById(Long.parseLong(commandArgumentWrapper.getCommandArgument("idCost")));
    }
}
