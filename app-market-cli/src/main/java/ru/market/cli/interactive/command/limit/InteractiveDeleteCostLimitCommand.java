package ru.market.cli.interactive.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.helper.command.CommandContext;
import ru.market.cli.interactive.helper.command.CommandContext.CommandArgument;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.element.IDElement;
import ru.market.cli.interactive.helper.command.TypeWrapper;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.client.rest.CostLimitRestClient;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveDeleteCostLimitCommand extends InteractiveCommonCommand {
    private CostLimitRestClient costLimitRestClient;
    private CommandHelper commandHelper;
    private CommandContext commandContext;

    @Autowired
    public InteractiveDeleteCostLimitCommand(CostLimitRestClient costLimitRestClient,
                                             CommandHelper commandHelper,
                                             CommandContext commandContext) {

        this.costLimitRestClient = costLimitRestClient;
        this.commandHelper = commandHelper;
        this.commandContext = commandContext;
    }

    @Override
    public String id() {
        return IDElement.DELETE_COST_LIMIT_CMD;
    }

    @Override
    public String performCommand(BufferedReader reader) {
        Long costLimitId = commandContext.getCommandArgument(CommandArgument.COST_LIMIT_ID);

        TypeWrapper<Boolean> typeWrapperRollbackOperation = new TypeWrapper<>();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите признак отката операций (true/false)", true,
                                (object, param) -> object.setTypeValue(Boolean.parseBoolean(param))
                        )
                ),
                typeWrapperRollbackOperation
        );

        if(isInterrupted){
            return IDElement.COST_LIMIT_COST_MENU;
        }

        costLimitRestClient.deleteById(costLimitId, typeWrapperRollbackOperation.getTypeValue());

        return IDElement.COST_LIMIT_CONTROL_MENU;
    }

    @Override
    public String getElementIdByException() {
        return IDElement.COST_LIMIT_CONTROL_MENU;
    }

    @Override
    public String getElementIdByRestClientException() {
        return IDElement.COST_LIMIT_CONTROL_MENU;
    }
}
