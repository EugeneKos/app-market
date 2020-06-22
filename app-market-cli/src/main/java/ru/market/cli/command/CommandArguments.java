package ru.market.cli.command;

import ru.ed.microlib.command.Argument;

public interface CommandArguments {
    Argument ID_ARG = new Argument("-i", "--id", true);

    Argument FIRST_NAME_ARG = new Argument("-f", "--firstName", true);
    Argument LAST_NAME_ARG = new Argument("-l", "--lastName", true);
    Argument MIDDLE_NAME_ARG = new Argument("-m", "--middleName", true);

    Argument USERNAME_ARG = new Argument("-u", "--username", true);
    Argument PASSWORD_ARG = new Argument("-p", "--password", true);
    Argument OLD_PASSWORD_ARG = new Argument("-op", "--oldPassword", true);
    Argument NEW_PASSWORD_ARG = new Argument("-np", "--newPassword", true);

    Argument BALANCE_ARG = new Argument("-b", "--balance", true);
    Argument SUM_ARG = new Argument("-s", "--sum", true);
    Argument AMOUNT_ARG = new Argument("-a", "--amount", false);
    Argument MANDATORY_AMOUNT_ARG = new Argument("-a", "--amount", true);
    Argument NAME_ARG = new Argument("-n", "--name", true);
    Argument DESCRIPTION_ARG = new Argument("-d", "--description", false);
    Argument MANDATORY_DESCRIPTION_ARG = new Argument("-d", "--description", true);
    Argument CATEGORY_ARG = new Argument("-c", "--category", true);

    Argument BEGIN_DATE_ARG = new Argument("-bd", "--beginDate", true);
    Argument END_DATE_ARG = new Argument("-ed", "--endDate", true);
    Argument MANDATORY_DATE_ARG = new Argument("-d", "--date", true);
    Argument DATE_ARG = new Argument("-d", "--date", false);
    Argument TIME_ARG = new Argument("-t", "--time", false);

    Argument COST_LIMIT_ID_ARG = new Argument("-cli", "--costLimitId", true);
    Argument MONEY_ACCOUNT_ID_ARG = new Argument("-mai", "--moneyAccountId", true);
    Argument FROM_MONEY_ACCOUNT_ID_ARG = new Argument("-fmai", "--fromMoneyAccountId", true);
    Argument TO_MONEY_ACCOUNT_ID_ARG = new Argument("-tmai", "--toMoneyAccountId", true);
    Argument COST_LIMIT_ROLLBACK_OP_ARG = new Argument("-rop", "--rollbackOperation", true);
    Argument OPERATION_TYPE_ARG = new Argument("-opt", "--rollbackOperation", true);
}
