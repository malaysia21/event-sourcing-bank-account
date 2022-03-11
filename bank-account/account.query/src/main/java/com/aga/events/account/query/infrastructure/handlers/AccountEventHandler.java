package com.aga.events.account.query.infrastructure.handlers;

import com.aga.events.account.common.events.AccountClosedEvent;
import com.aga.events.account.common.events.AccountOpenedEvent;
import com.aga.events.account.common.events.MoneyDepositedEvent;
import com.aga.events.account.common.events.MoneyWithdrawEvent;
import com.aga.events.account.query.domain.AccountRepository;
import com.aga.events.account.query.domain.BankAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountEventHandler implements EventHandler{

    private final AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreateDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }

    @Override
    public void on(MoneyDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if(bankAccount.isEmpty()){
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(MoneyWithdrawEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if(bankAccount.isEmpty()){
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }
}
