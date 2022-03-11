package com.aga.events.cqrs.core.infrastructure;

import com.aga.events.cqrs.core.domain.BaseEntity;
import com.aga.events.cqrs.core.queries.BaseQuery;
import com.aga.events.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {

    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);

    <U extends BaseEntity> List<U> send(BaseQuery command);
}
