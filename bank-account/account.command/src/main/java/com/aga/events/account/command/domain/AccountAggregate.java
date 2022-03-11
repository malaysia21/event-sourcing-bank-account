package com.aga.events.account.command.domain;

import com.aga.events.account.command.api.commands.OpenAccountCommand;
import com.aga.events.account.common.events.AccountClosedEvent;
import com.aga.events.account.common.events.AccountOpenedEvent;
import com.aga.events.account.common.events.MoneyDepositedEvent;
import com.aga.events.account.common.events.MoneyWithdrawEvent;
import com.aga.events.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private Boolean active;
    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createDate(LocalDateTime.now())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositMoney(double amount) {
        if(!this.active) {
            throw new IllegalStateException("Money cannot be deposit into a closed account");
        }
        if(amount <= 0) {
            throw new IllegalStateException("The deposit amount must be greater then zero");
        }
        raiseEvent(MoneyDepositedEvent.builder()
                        .id(this.id)
                        .amount(amount)
                        .build()
                );
    }

    public void apply(MoneyDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawMoney(double amount) {
        if(!this.active) {
            throw new IllegalStateException("Money cannot be withdraw into a closed account");
        }
        raiseEvent(MoneyWithdrawEvent.builder()
                .id(this.id)
                .amount(amount)
                .build()
        );
    }

    public void apply(MoneyWithdrawEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount() {
        if(!this.active) {
            throw new IllegalStateException("The bank account has already been closed");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build()
        );
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }

    public double getBalance() {
        return this.balance;
    }

    public Boolean getActive() {
        return active;
    }
}
