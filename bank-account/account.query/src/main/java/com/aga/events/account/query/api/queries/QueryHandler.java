package com.aga.events.account.query.api.queries;

import com.aga.events.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {

    List<BaseEntity> handler (FindAllAccountsQuery query);
    List<BaseEntity> handler (FindAccountByIdQuery query);
    List<BaseEntity> handler (FindAccountByHolderQuery query);
    List<BaseEntity> handler (FindAccountsWithBalanceQuery query);
}
