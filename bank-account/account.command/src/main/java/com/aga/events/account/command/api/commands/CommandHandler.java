package com.aga.events.account.command.api.commands;


public interface CommandHandler {

    void handle(OpenAccountCommand command);
    void handle(DepositMoneyCommand command);
    void handle(WithdrawMoneyCommand command);
    void handle(CloseAccountCommand command);
    void handle(RestoreReadDbCommand command);
}
