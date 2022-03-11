package com.aga.events.account.query.api.queries;

import com.aga.events.account.query.api.queries.dto.EqualityType;
import com.aga.events.account.query.domain.AccountRepository;
import com.aga.events.account.query.domain.BankAccount;
import com.aga.events.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountQueryHandler implements QueryHandler {

    private final AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handler(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccounts.forEach(bankAccountList::add);
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handler(FindAccountByIdQuery query) {
        Optional<BankAccount> accountById = accountRepository.findById(query.getId());
        if(accountById.isEmpty()) {
            return null;
        }
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(accountById.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handler(FindAccountByHolderQuery query) {
        Optional<BankAccount> accountByHolder = accountRepository.findByAccountHolder(query.getAccountHolder());
        if(accountByHolder.isEmpty()) {
            return null;
        }
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(accountByHolder.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handler(FindAccountsWithBalanceQuery query) {
       return query.getEqualityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getAmount())
                : accountRepository.findByBalanceLessThan(query.getAmount());
    }
}
