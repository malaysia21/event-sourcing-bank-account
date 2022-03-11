package com.aga.events.account.command.infrastructure;

import com.aga.events.account.command.domain.AccountAggregate;
import com.aga.events.cqrs.core.domain.AggregateRoot;
import com.aga.events.cqrs.core.events.BaseEvent;
import com.aga.events.cqrs.core.handlers.EventSourcingHandler;
import com.aga.events.cqrs.core.infrastructure.EventStore;
import com.aga.events.cqrs.core.produces.EventProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@AllArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;
    private final EventProducer eventProducer;


    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregatesIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregatesIds) {
            var aggregate = getById(aggregateId);
            if (aggregate == null || !aggregate.getActive()) {
                continue;
            }
            var events = eventStore.getEvents(aggregateId);

            for (var event : events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
