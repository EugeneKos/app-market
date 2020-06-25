package ru.market.cli.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.command.CommandUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.result.ResultDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.command.CommandArguments.DATE_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_DESCRIPTION_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_AMOUNT_ARG;
import static ru.market.cli.command.CommandArguments.MONEY_ACCOUNT_ID_ARG;
import static ru.market.cli.command.CommandArguments.TIME_ARG;

@Service
public class EnrollOperationCommand implements Command {
    private OperationRestClient operationRestClient;
    private Printer printer;

    @Autowired
    public EnrollOperationCommand(OperationRestClient operationRestClient, Printer printer) {
        this.operationRestClient = operationRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "operation-enroll";
    }

    @Override
    public String getDescription() {
        return "Команда на операцию зачисления";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{MANDATORY_AMOUNT_ARG, MANDATORY_DESCRIPTION_ARG, MONEY_ACCOUNT_ID_ARG, DATE_ARG, TIME_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        OperationEnrollDebitDTO enrollDebitDTO = EnrollDebitOperationUtil.createEnrollDebitDTO(arguments);

        ResultDTO result = operationRestClient.enrollment(enrollDebitDTO);
        printer.printTable(CommandUtils.createResultsTableToPrint(Collections.singletonList(result)));
    }
}
