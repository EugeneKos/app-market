package ru.market.cli.printer;

import ru.market.dto.cost.CostDTO;
import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitInfoDTO;
import ru.market.dto.money.MoneyAccountDTO;
import ru.market.dto.operation.OperationDTO;
import ru.market.dto.person.PersonDTO;
import ru.market.dto.result.ResultDTO;
import ru.market.dto.user.UserDTO;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public final class PrinterUtils {
    private PrinterUtils() {}

    public static LinkedHashMap<String, List<String>> createUsersTableToPrint(Collection<UserDTO> users){
        LinkedHashMap<String, List<String>> tableToPrint = new LinkedHashMap<>();

        List<List<String>> columns = ru.market.cli.printer.Printer.createEmptyColumns(6);

        for (UserDTO user : users){
            columns.get(0).add(String.valueOf(user.getId()));
            columns.get(1).add(user.getUsername());
            columns.get(2).add(String.valueOf(user.getPerson().getId()));
            columns.get(3).add(user.getPerson().getFirstName());
            columns.get(4).add(user.getPerson().getLastName());
            columns.get(5).add(user.getPerson().getMiddleName());
        }

        tableToPrint.put("Id", columns.get(0));
        tableToPrint.put("Имя пользователя", columns.get(1));
        tableToPrint.put("Id пользователя", columns.get(2));
        tableToPrint.put("Фамилия", columns.get(3));
        tableToPrint.put("Имя", columns.get(4));
        tableToPrint.put("Отчество", columns.get(5));

        return tableToPrint;
    }

    public static LinkedHashMap<String, List<String>> createPersonsTableToPrint(Collection<PersonDTO> persons){
        LinkedHashMap<String, List<String>> tableToPrint = new LinkedHashMap<>();

        List<List<String>> columns = ru.market.cli.printer.Printer.createEmptyColumns(4);

        for (PersonDTO person : persons){
            columns.get(0).add(String.valueOf(person.getId()));
            columns.get(1).add(person.getFirstName());
            columns.get(2).add(person.getLastName());
            columns.get(3).add(person.getMiddleName());
        }

        tableToPrint.put("Id", columns.get(0));
        tableToPrint.put("Фамилия", columns.get(1));
        tableToPrint.put("Имя", columns.get(2));
        tableToPrint.put("Отчество", columns.get(3));

        return tableToPrint;
    }

    public static LinkedHashMap<String, List<String>> createMoneyAccountsTableToPrint(Collection<MoneyAccountDTO> moneyAccounts){
        LinkedHashMap<String, List<String>> tableToPrint = new LinkedHashMap<>();

        List<List<String>> columns = ru.market.cli.printer.Printer.createEmptyColumns(5);

        for (MoneyAccountDTO moneyAccount : moneyAccounts){
            columns.get(0).add(String.valueOf(moneyAccount.getId()));
            columns.get(1).add(moneyAccount.getBalance());
            columns.get(2).add(moneyAccount.getName());
            columns.get(3).add(moneyAccount.getDescription());
            columns.get(4).add(moneyAccount.getDateCreatedStr());
        }

        tableToPrint.put("Id", columns.get(0));
        tableToPrint.put("Баланс", columns.get(1));
        tableToPrint.put("Название", columns.get(2));
        tableToPrint.put("Описание", columns.get(3));
        tableToPrint.put("Дата создания", columns.get(4));

        return tableToPrint;
    }

    public static LinkedHashMap<String, List<String>> createOperationsTableToPrint(Collection<OperationDTO> operations){
        LinkedHashMap<String, List<String>> tableToPrint = new LinkedHashMap<>();

        List<List<String>> columns = ru.market.cli.printer.Printer.createEmptyColumns(8);

        for (OperationDTO operation : operations){
            columns.get(0).add(String.valueOf(operation.getId()));
            columns.get(1).add(operation.getAmount());
            columns.get(2).add(operation.getDescription());
            columns.get(3).add(operation.getDateStr());
            columns.get(4).add(operation.getTimeStr());
            columns.get(5).add(operation.getOperationType());
            columns.get(6).add(operation.getNewBalance());
            columns.get(7).add(operation.getOldBalance());
        }

        tableToPrint.put("Id", columns.get(0));
        tableToPrint.put("Сумма операции", columns.get(1));
        tableToPrint.put("Описание", columns.get(2));
        tableToPrint.put("Дата операции", columns.get(3));
        tableToPrint.put("Время операции", columns.get(4));
        tableToPrint.put("Тип операции", columns.get(5));
        tableToPrint.put("Баланс после", columns.get(6));
        tableToPrint.put("Баланс до", columns.get(7));

        return tableToPrint;
    }

    public static LinkedHashMap<String, List<String>> createCostsTableToPrint(Collection<CostDTO> costs){
        LinkedHashMap<String, List<String>> tableToPrint = new LinkedHashMap<>();

        List<List<String>> columns = ru.market.cli.printer.Printer.createEmptyColumns(8);

        for (CostDTO cost : costs){
            columns.get(0).add(String.valueOf(cost.getId()));
            columns.get(1).add(cost.getSum());
            columns.get(2).add(cost.getCategory());
            columns.get(3).add(cost.getDescription());
            columns.get(4).add(cost.getDateStr());
            columns.get(5).add(cost.getTimeStr());
            columns.get(6).add(String.valueOf(cost.getCostLimitId()));
            columns.get(7).add(String.valueOf(cost.getMoneyAccountId()));
        }

        tableToPrint.put("Id", columns.get(0));
        tableToPrint.put("Сумма", columns.get(1));
        tableToPrint.put("Категория", columns.get(2));
        tableToPrint.put("Описание", columns.get(3));
        tableToPrint.put("Дата затраты", columns.get(4));
        tableToPrint.put("Время затраты", columns.get(5));
        tableToPrint.put("Id лимита", columns.get(6));
        tableToPrint.put("Id денежного счета", columns.get(7));

        return tableToPrint;
    }

    public static LinkedHashMap<String, List<String>> createCostLimitsTableToPrint(Collection<? extends CostLimitDTO> costLimits){
        LinkedHashMap<String, List<String>> tableToPrint = new LinkedHashMap<>();

        List<List<String>> columns = ru.market.cli.printer.Printer.createEmptyColumns(5);

        for (CostLimitDTO costLimit : costLimits){
            columns.get(0).add(String.valueOf(costLimit.getId()));
            columns.get(1).add(costLimit.getSum());
            columns.get(2).add(costLimit.getDescription());
            columns.get(3).add(costLimit.getBeginDateStr());
            columns.get(4).add(costLimit.getEndDateStr());
        }

        tableToPrint.put("Id", columns.get(0));
        tableToPrint.put("Сумма", columns.get(1));
        tableToPrint.put("Описание", columns.get(2));
        tableToPrint.put("Дата начала", columns.get(3));
        tableToPrint.put("Дата завершения", columns.get(4));

        return tableToPrint;
    }

    public static LinkedHashMap<String, List<String>> createCostLimitInfosTableToPrint(Collection<CostLimitInfoDTO> costLimitInfos){
        LinkedHashMap<String, List<String>> tableToPrint = createCostLimitsTableToPrint(costLimitInfos);

        List<List<String>> columns = ru.market.cli.printer.Printer.createEmptyColumns(3);

        for (CostLimitInfoDTO costLimitInfo : costLimitInfos){
            columns.get(0).add(costLimitInfo.getRemain());
            columns.get(1).add(costLimitInfo.getSpendingPerDay());
            columns.get(2).add(costLimitInfo.getAvailableToday());
        }

        tableToPrint.put("Остаток лимита", columns.get(0));
        tableToPrint.put("Затраты за день", columns.get(1));
        tableToPrint.put("Доступно на сегодня", columns.get(2));

        return tableToPrint;
    }

    public static LinkedHashMap<String, List<String>> createResultsTableToPrint(Collection<ResultDTO> results){
        LinkedHashMap<String, List<String>> tableToPrint = new LinkedHashMap<>();

        List<List<String>> columns = ru.market.cli.printer.Printer.createEmptyColumns(2);

        for (ResultDTO result : results){
            columns.get(0).add(result.getStatus().name());
            columns.get(1).add(result.getDescription());
        }

        tableToPrint.put("Status", columns.get(0));
        tableToPrint.put("Description", columns.get(1));

        return tableToPrint;
    }
}
