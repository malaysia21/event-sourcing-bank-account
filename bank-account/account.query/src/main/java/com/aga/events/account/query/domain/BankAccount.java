package com.aga.events.account.query.domain;

import com.aga.events.account.common.dto.AccountType;
import com.aga.events.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount extends BaseEntity {

    @Id
    private String id;
    private String accountHolder;
    private LocalDateTime creationDate;
    private AccountType accountType;
    private double balance;
}
