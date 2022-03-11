package com.aga.events.account.command.api.commands;

import com.aga.events.account.command.domain.AccountAggregate;
import com.aga.events.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler {

    @Autowired
    private EventSourcingHandler<AccountAggregate> accountEventSourcingHandler;

    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        accountEventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositMoneyCommand command) {
        var aggregate = accountEventSourcingHandler.getById(command.getId());
        aggregate.depositMoney(command.getAmount());
        accountEventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawMoneyCommand command) {
        var aggregate = accountEventSourcingHandler.getById(command.getId());
        if(command.getAmount() > aggregate.getBalance()) {
            throw new IllegalStateException("Withdraw declined, insufficient money");
        }
        aggregate.withdrawMoney(command.getAmount());
        accountEventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = accountEventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        accountEventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(RestoreReadDbCommand command) {
        accountEventSourcingHandler.republishEvents();
    }
}
