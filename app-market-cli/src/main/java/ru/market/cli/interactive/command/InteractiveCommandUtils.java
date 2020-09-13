package ru.market.cli.interactive.command;

import ru.market.cli.interactive.helper.command.CommandDetail;
import ru.market.cli.interactive.helper.command.CommandHelper;
import ru.market.cli.interactive.helper.command.TypeWrapper;
import ru.market.dto.operation.OperationEnrollDebitDTO;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.BiConsumer;

public final class InteractiveCommandUtils {
    private InteractiveCommandUtils(){
    }

    public static boolean fillCostLimitIdArgument(BufferedReader reader, CommandHelper commandHelper, TypeWrapper<Long> typeWrapper){
        return commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите id лимита", true,
                                (object, param) -> object.setTypeValue(Long.parseLong(param))
                        )
                ),
                typeWrapper
        );
    }

    public static boolean fillDateArgument(BufferedReader reader, CommandHelper commandHelper, TypeWrapper<String> typeWrapper){
        return commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(new CommandDetail<>("Введите дату", true, TypeWrapper::setTypeValue)),
                typeWrapper
        );
    }

    public static boolean fillMoneyAccountIdArgument(BufferedReader reader, CommandHelper commandHelper, TypeWrapper<Long> typeWrapper){
        return commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                Collections.singletonList(
                        new CommandDetail<>("Введите id денежного счета", true,
                                (object, param) -> object.setTypeValue(Long.parseLong(param))
                        )
                ),
                typeWrapper
        );
    }

    public static boolean fillEnrollDebitOperationArguments(BufferedReader reader, CommandHelper commandHelper,
                                                            OperationEnrollDebitDTO enrollDebitDTO){

        return commandHelper.fillBusinessObjectByCommandDetail(
                reader,
                new ArrayList<>(Arrays.asList(
                        new CommandDetail<>("Введите сумму операции", true,
                                OperationEnrollDebitDTO::setAmount
                        ),
                        new CommandDetail<>("Введите описание операции", true,
                                OperationEnrollDebitDTO::setDescription
                        ),
                        new CommandDetail<>("Введите id денежного счета", true,
                                (object, param) -> object.setMoneyAccountId(Long.parseLong(param))
                        ),
                        new CommandDetail<>("Введите дату операции", false,
                                OperationEnrollDebitDTO::setDateStr
                        ),
                        new CommandDetail<>("Введите время операции", false,
                                OperationEnrollDebitDTO::setTimeStr
                        )
                )),
                enrollDebitDTO
        );
    }

    public static void performCommandWithCostLimitIdAndDateArguments(BufferedReader reader, CommandHelper commandHelper,
                                                                     BiConsumer<TypeWrapper<Long>, TypeWrapper<String>> performer){

        TypeWrapper<Long> typeWrapperIdCostLimit = new TypeWrapper<>();

        boolean isInterrupted = fillCostLimitIdArgument(reader, commandHelper, typeWrapperIdCostLimit);

        if(isInterrupted){
            return;
        }

        TypeWrapper<String> typeWrapperDate = new TypeWrapper<>();

        isInterrupted = fillDateArgument(reader, commandHelper, typeWrapperDate);

        if(isInterrupted){
            return;
        }

        performer.accept(typeWrapperIdCostLimit, typeWrapperDate);
    }
}
