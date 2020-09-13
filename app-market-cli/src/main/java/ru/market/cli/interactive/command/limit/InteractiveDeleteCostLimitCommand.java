package ru.market.cli.interactive.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.CommandArgumentWrapper;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.client.rest.CostLimitRestClient;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class InteractiveDeleteCostLimitCommand extends InteractiveCommonCommand {
    private CostLimitRestClient costLimitRestClient;
    private CommandHelper commandHelper;

    @Autowired
    public InteractiveDeleteCostLimitCommand(CostLimitRestClient costLimitRestClient, CommandHelper commandHelper) {
        this.costLimitRestClient = costLimitRestClient;
        this.commandHelper = commandHelper;
    }

    @Override
    public String name() {
        return "Удалить лимит на затраты";
    }

    @Override
    public void perform(BufferedReader reader) {
        CommandArgumentWrapper commandArgumentWrapper = new CommandArgumentWrapper();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите id лимита", true,
                                (object, param) -> object.addCommandArgument("idCostLimit", param)
                        ),
                        new CommandDetail<>("Введите признак отката операций (true/false)", true,
                                (object, param) -> object.addCommandArgument("rollbackOperations", param)
                        )
                )),
                commandArgumentWrapper
        );


        if(isInterrupted){
            return;
        }

        costLimitRestClient.deleteById(
                Long.parseLong(commandArgumentWrapper.getCommandArgument("idCostLimit")),
                Boolean.parseBoolean(commandArgumentWrapper.getCommandArgument("rollbackOperations"))
        );
    }
}
