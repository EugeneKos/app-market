package ru.market.cli.interactive.command.limit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommandUtils;
import ru.market.cli.interactive.command.InteractiveCommonCommand;
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
        TypeWrapper<Long> typeWrapperIdCostLimit = new TypeWrapper<>();

        boolean isInterrupted = InteractiveCommandUtils.fillCostLimitIdArgument(reader, commandHelper, typeWrapperIdCostLimit);

        if(isInterrupted){
            return;
        }

        TypeWrapper<Boolean> typeWrapperRollbackOperation = new TypeWrapper<>();

        isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите признак отката операций (true/false)", true,
                                (object, param) -> object.setTypeValue(Boolean.parseBoolean(param))
                        )
                ),
                typeWrapperRollbackOperation
        );

        if(isInterrupted){
            return;
        }

        costLimitRestClient.deleteById(typeWrapperIdCostLimit.getTypeValue(), typeWrapperRollbackOperation.getTypeValue());
    }
}
