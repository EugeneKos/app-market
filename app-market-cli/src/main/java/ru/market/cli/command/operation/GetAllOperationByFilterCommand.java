package ru.market.cli.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationFilterDTO;

import java.util.Map;
import java.util.Set;

import static ru.market.cli.command.CommandArguments.AMOUNT_ARG;
import static ru.market.cli.command.CommandArguments.DATE_ARG;
import static ru.market.cli.command.CommandArguments.DESCRIPTION_ARG;
import static ru.market.cli.command.CommandArguments.MONEY_ACCOUNT_ID_ARG;
import static ru.market.cli.command.CommandArguments.OPERATION_TYPE_ARG;

@Service
public class GetAllOperationByFilterCommand implements Command {
    private OperationRestClient operationRestClient;

    @Autowired
    public GetAllOperationByFilterCommand(OperationRestClient operationRestClient) {
        this.operationRestClient = operationRestClient;
    }

    @Override
    public String getName() {
        return "operation-get-all-filter";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{MONEY_ACCOUNT_ID_ARG, AMOUNT_ARG, DATE_ARG, DESCRIPTION_ARG, OPERATION_TYPE_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        OperationFilterDTO filterDTO = OperationFilterDTO.builder()
                .amount(arguments.get(AMOUNT_ARG))
                .dateStr(arguments.get(DATE_ARG))
                .description(arguments.get(DESCRIPTION_ARG))
                .operationType(arguments.get(OPERATION_TYPE_ARG))
                .build();

        Set<OperationDTO> operations = operationRestClient.getAllByMoneyAccountIdAndFilter(
                Long.parseLong(arguments.get(MONEY_ACCOUNT_ID_ARG)), filterDTO
        );

        Printer.print(operations);
    }
}
