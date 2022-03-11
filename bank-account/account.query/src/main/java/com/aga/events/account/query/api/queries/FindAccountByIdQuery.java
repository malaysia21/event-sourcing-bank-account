package com.aga.events.account.query.api.queries;

import com.aga.events.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByIdQuery extends BaseQuery {

    private String id;
}
