package ru.market.cli.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.result.ResultDTO;

import java.util.Map;

import static ru.market.cli.command.CommandArguments.DATE_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_DESCRIPTION_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_AMOUNT_ARG;
import static ru.market.cli.command.CommandArguments.MONEY_ACCOUNT_ID_ARG;
import static ru.market.cli.command.CommandArguments.TIME_ARG;

@Service
public class EnrollOperationCommand implements Command {
    private OperationRestClient operationRestClient;

    @Autowired
    public EnrollOperationCommand(OperationRestClient operationRestClient) {
        this.operationRestClient = operationRestClient;
    }

    @Override
    public String getName() {
        return "operation-enroll";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{MANDATORY_AMOUNT_ARG, MANDATORY_DESCRIPTION_ARG, MONEY_ACCOUNT_ID_ARG, DATE_ARG, TIME_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        OperationEnrollDebitDTO enrollDebitDTO = EnrollDebitOperationUtil.createEnrollDebitDTO(arguments);

        ResultDTO result = operationRestClient.enrollment(enrollDebitDTO);
        Printer.print(result);
    }
}
