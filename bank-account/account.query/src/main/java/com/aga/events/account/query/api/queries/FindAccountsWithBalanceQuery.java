package com.aga.events.account.query.api.queries;

import com.aga.events.account.query.api.queries.dto.EqualityType;
import com.aga.events.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountsWithBalanceQuery extends BaseQuery {

    private final EqualityType equalityType;
    private final double amount;
}
