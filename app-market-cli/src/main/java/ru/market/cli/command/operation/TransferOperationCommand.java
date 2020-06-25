package ru.market.cli.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.command.CommandUtils;
import ru.market.cli.printer.Printer;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationTransferDTO;
import ru.market.dto.result.ResultDTO;

import java.util.Collections;
import java.util.Map;

import static ru.market.cli.command.CommandArguments.DATE_ARG;
import static ru.market.cli.command.CommandArguments.FROM_MONEY_ACCOUNT_ID_ARG;
import static ru.market.cli.command.CommandArguments.MANDATORY_AMOUNT_ARG;
import static ru.market.cli.command.CommandArguments.TIME_ARG;
import static ru.market.cli.command.CommandArguments.TO_MONEY_ACCOUNT_ID_ARG;

@Service
public class TransferOperationCommand implements Command {
    private OperationRestClient operationRestClient;
    private Printer printer;

    @Autowired
    public TransferOperationCommand(OperationRestClient operationRestClient, Printer printer) {
        this.operationRestClient = operationRestClient;
        this.printer = printer;
    }

    @Override
    public String getName() {
        return "operation-transfer";
    }

    @Override
    public String getDescription() {
        return "Команда на операцию перевода";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{FROM_MONEY_ACCOUNT_ID_ARG, TO_MONEY_ACCOUNT_ID_ARG, MANDATORY_AMOUNT_ARG, DATE_ARG, TIME_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        OperationTransferDTO transferDTO = OperationTransferDTO.operationTransferBuilder()
                .fromMoneyAccountId(Long.parseLong(arguments.get(FROM_MONEY_ACCOUNT_ID_ARG)))
                .toMoneyAccountId(Long.parseLong(arguments.get(TO_MONEY_ACCOUNT_ID_ARG)))
                .build();

        transferDTO.setAmount(arguments.get(MANDATORY_AMOUNT_ARG));
        transferDTO.setDateStr(arguments.get(DATE_ARG));
        transferDTO.setTimeStr(arguments.get(TIME_ARG));

        ResultDTO result = operationRestClient.transfer(transferDTO);
        printer.printTable(CommandUtils.createResultsTableToPrint(Collections.singletonList(result)));
    }
}
