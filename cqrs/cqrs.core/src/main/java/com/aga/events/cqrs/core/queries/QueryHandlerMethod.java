package com.aga.events.cqrs.core.queries;

import com.aga.events.cqrs.core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {

    List<BaseEntity> handle(T query);
}
