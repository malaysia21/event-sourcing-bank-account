package com.aga.events.cqrs.core.handlers;

import com.aga.events.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {

    void save(AggregateRoot aggregate);
    T getById(String id);
    void republishEvents();
}
