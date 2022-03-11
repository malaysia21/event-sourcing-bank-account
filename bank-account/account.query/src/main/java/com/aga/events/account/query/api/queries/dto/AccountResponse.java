package com.aga.events.account.query.api.queries.dto;

import com.aga.events.account.common.dto.BaseResponse;
import com.aga.events.account.query.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse extends BaseResponse {
    private List<BankAccount> accounts;

    public AccountResponse(String message) {
        super(message);
    }
}
