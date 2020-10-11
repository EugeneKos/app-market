package ru.market.cli.interactive.element;

public interface IDElement {
    // Menu ID
    String MAIN_MENU = "MAIN_MENU";
    String APPLICATION_MENU = "APPLICATION_MENU";
    String MONEY_ACCOUNT_CONTROL_MENU = "MONEY_ACCOUNT_CONTROL_MENU";
    String MONEY_ACCOUNT_OPERATION_MENU = "MONEY_ACCOUNT_OPERATION_MENU";
    String COST_LIMIT_CONTROL_MENU = "COST_LIMIT_CONTROL_MENU";
    String COST_LIMIT_COST_MENU = "COST_LIMIT_COST_MENU";
    String COST_MENU = "COST_MENU";
    String USER_CONTROL_MENU = "USER_CONTROL_MENU";

    // Command ID
    String EXIT_CMD = "EXIT";

    // Authenticate/Registration CMD
    String REGISTRATION_CMD = "REGISTRATION_CMD";
    String AUTHENTICATE_CMD = "AUTHENTICATE_CMD";
    String LOGOUT_CMD = "LOGOUT_CMD";

    // User CMD
    String GET_USER_CMD = "GET_USER_CMD";
    String UPDATE_USER_CMD = "UPDATE_USER_CMD";
    String DELETE_USER_CMD = "DELETE_USER_CMD";
    String CHANGE_USERNAME_CMD = "CHANGE_USERNAME_CMD";
    String CHANGE_PASSWORD_CMD = "CHANGE_PASSWORD_CMD";

    // Money Account CMD
    String CREATE_MONEY_ACCOUNT_CMD = "CREATE_MONEY_ACCOUNT_CMD";
    String GET_MONEY_ACCOUNT_CMD = "GET_MONEY_ACCOUNT_CMD";
    String GET_ALL_MONEY_ACCOUNT_CMD = "GET_ALL_MONEY_ACCOUNT_CMD";
    String DELETE_MONEY_ACCOUNT_CMD = "DELETE_MONEY_ACCOUNT_CMD";

    // Operation CMD
    String ENROLL_OPERATION_CMD = "ENROLL_OPERATION_CMD";
    String DEBIT_OPERATION_CMD = "DEBIT_OPERATION_CMD";
    String TRANSFER_OPERATION_CMD = "TRANSFER_OPERATION_CMD";
    String GET_ALL_OPERATION_CMD = "GET_ALL_OPERATION_CMD";
    String GET_ALL_OPERATION_BY_FILTER_CMD = "GET_ALL_OPERATION_BY_FILTER_CMD";

    // Cost Limit CMD
    String CREATE_COST_LIMIT_CMD = "CREATE_COST_LIMIT_CMD";
    String GET_ALL_COST_LIMIT_CMD = "GET_ALL_COST_LIMIT_CMD";
    String GET_COST_LIMIT_INFO_CMD = "GET_COST_LIMIT_INFO_CMD";
    String DELETE_COST_LIMIT_CMD = "DELETE_COST_LIMIT_CMD";

    // Cost CMD
    String CREATE_COST_CMD = "CREATE_COST_CMD";
    String GET_ALL_COST_CMD = "GET_ALL_COST_CMD";
    String GET_ALL_COST_BY_DATE_CMD = "GET_ALL_COST_BY_DATE_CMD";
    String UPDATE_COST_CMD = "UPDATE_COST_CMD";
    String DELETE_COST_CMD = "DELETE_COST_CMD";
}