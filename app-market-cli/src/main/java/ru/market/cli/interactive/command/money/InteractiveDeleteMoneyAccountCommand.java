package ru.market.cli.interactive.command.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.market.cli.interactive.command.InteractiveCommonCommand;
import ru.market.cli.interactive.helper.command.TypeWrapper;
import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.client.rest.MoneyAccountRestClient;

import java.io.BufferedReader;
import java.util.Collections;

@Service
public class InteractiveDeleteMoneyAccountCommand extends InteractiveCommonCommand {
    private MoneyAccountRestClient moneyAccountRestClient;
    private CommandHelper commandHelper;

    @Autowired
    public InteractiveDeleteMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, CommandHelper commandHelper) {
        this.moneyAccountRestClient = moneyAccountRestClient;
        this.commandHelper = commandHelper;
    }

    @Override
    public String name() {
        return "Удалить денежный счет";
    }

    @Override
    public void perform(BufferedReader reader) {
        TypeWrapper<Long> typeWrapper = new TypeWrapper<>();

        boolean isInterrupted = commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите id денежного счета", true,
                                (object, param) -> object.setTypeValue(Long.parseLong(param))
                        )
                ),
                typeWrapper
        );

        if(isInterrupted){
            return;
        }

        moneyAccountRestClient.deleteById(typeWrapper.getTypeValue());
    }
}
