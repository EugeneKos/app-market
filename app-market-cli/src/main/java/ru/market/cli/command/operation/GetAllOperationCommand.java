package ru.market.cli.command.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import ru.market.cli.utils.Printer;
import ru.market.client.rest.OperationRestClient;
import ru.market.dto.operation.OperationDTO;

import java.util.Map;
import java.util.Set;

import static ru.market.cli.command.CommandArguments.MONEY_ACCOUNT_ID_ARG;

@Service
public class GetAllOperationCommand implements Command {
    private OperationRestClient operationRestClient;

    @Autowired
    public GetAllOperationCommand(OperationRestClient operationRestClient) {
        this.operationRestClient = operationRestClient;
    }

    @Override
    public String getName() {
        return "operation-get-all";
    }

    @Override
    public Argument[] getArguments() {
        return new Argument[]{MONEY_ACCOUNT_ID_ARG};
    }

    @Override
    public void perform(Map<Argument, String> arguments) {
        Set<OperationDTO> operations = operationRestClient.getAllByMoneyAccountId(
                Long.parseLong(arguments.get(MONEY_ACCOUNT_ID_ARG))
        );

        Printer.print(operations);
    }
}
