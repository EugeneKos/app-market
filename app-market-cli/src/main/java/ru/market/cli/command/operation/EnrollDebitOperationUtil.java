package ru.market.cli.command.operation;

import ru.ed.microlib.command.Argument;

import ru.market.dto.operation.OperationEnrollDebitDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.DATE_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_AMOUNT_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_DESCRIPTION_ARG;
import static ru.market.cli.command.CommandArguments.MONEY_ACCOUNT_ID_ARG;
import static ru.market.cli.command.CommandArguments.TIME_ARG;

final class EnrollDebitOperationUtil {
    private EnrollDebitOperationUtil(){}

    static OperationEnrollDebitDTO createEnrollDebitDTO(Map<Argument, String> arguments){
        OperationEnrollDebitDTO enrollDebitDTO = OperationEnrollDebitDTO.operationEnrollBuilder()
                .description(arguments.get(MANDATORY_DESCRIPTION_ARG))
                .moneyAccountId(Long.parseLong(arguments.get(MONEY_ACCOUNT_ID_ARG)))
                .build();

        enrollDebitDTO.setAmount(arguments.get(MANDATORY_AMOUNT_ARG));
        enrollDebitDTO.setDateStr(arguments.get(DATE_ARG));
        enrollDebitDTO.setTimeStr(arguments.get(TIME_ARG));

        return enrollDebitDTO;
    }
}
