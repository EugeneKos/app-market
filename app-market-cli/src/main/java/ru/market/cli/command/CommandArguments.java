package ru.market.cli.command;

import ru.ed.microlib.command.Argument;

public interface CommandArguments {
    Argument ID_ARG = new Argument("-i", "--id", true);

    Argument FIRST_NAME_ARG = new Argument("-fn", "--firstName", true);
    Argument LAST_NAME_ARG = new Argument("-ln", "--lastName", true);
    Argument MIDDLE_NAME_ARG = new Argument("-mn", "--middleName", true);

    Argument USERNAME_ARG = new Argument("-u", "--username", true);
    Argument PASSWORD_ARG = new Argument("-p", "--password", true);
    Argument OLD_PASSWORD_ARG = new Argument("-op", "--oldPassword", true);
    Argument NEW_PASSWORD_ARG = new Argument("-np", "--newPassword", true);

    Argument BALANCE_ARG = new Argument("-bn", "--balance", true);
    Argument SUM_ARG = new Argument("-sm", "--sum", true);
    Argument AMOUNT_ARG = new Argument("-amt", "--amount", false);
    Argument MANDATORY_AMOUNT_ARG = new Argument("-amt", "--amount", true);
    Argument NAME_ARG = new Argument("-n", "--name", true);
    Argument DESCRIPTION_ARG = new Argument("-dsc", "--description", false);
    Argument MANDATORY_DESCRIPTION_ARG = new Argument("-dsc", "--description", true);
    Argument CATEGORY_ARG = new Argument("-cat", "--category", true);

    Argument BEGIN_DATE_ARG = new Argument("-bd", "--beginDate", true);
    Argument END_DATE_ARG = new Argument("-ed", "--endDate", true);
    Argument MANDATORY_DATE_ARG = new Argument("-dt", "--date", true);
    Argument DATE_ARG = new Argument("-dt", "--date", false);
    Argument TIME_ARG = new Argument("-tm", "--time", false);

    Argument COST_LIMIT_ID_ARG = new Argument("-cli", "--costLimitId", true);
    Argument MONEY_ACCOUNT_ID_ARG = new Argument("-mai", "--moneyAccountId", true);
    Argument FROM_MONEY_ACCOUNT_ID_ARG = new Argument("-fmai", "--fromMoneyAccountId", true);
    Argument TO_MONEY_ACCOUNT_ID_ARG = new Argument("-tmai", "--toMoneyAccountId", true);
    Argument COST_LIMIT_ROLLBACK_OP_ARG = new Argument("-rop", "--rollbackOperation", true);
    Argument OPERATION_TYPE_ARG = new Argument("-opt", "--operationType", false);
}
