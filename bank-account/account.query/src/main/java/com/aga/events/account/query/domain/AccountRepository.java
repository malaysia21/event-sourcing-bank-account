package com.aga.events.account.query.domain;

import com.aga.events.cqrs.core.domain.BaseEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<BankAccount, String> {
    Optional<BankAccount> findByAccountHolder(String accountHolder);
    List<BaseEntity> findByBalanceGreaterThan(double balance);
    List<BaseEntity> findByBalanceLessThan(double balance);
}
