package com.aga.events.account.command.infrastructure;

import com.aga.events.account.command.domain.AccountAggregate;
import com.aga.events.account.command.domain.EventStoreRepository;
import com.aga.events.cqrs.core.events.BaseEvent;
import com.aga.events.cqrs.core.events.EventModel;
import com.aga.events.cqrs.core.exceptions.AggregateNotFoundException;
import com.aga.events.cqrs.core.exceptions.ConcurrencyException;
import com.aga.events.cqrs.core.infrastructure.EventStore;
import com.aga.events.cqrs.core.produces.EventProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(LocalDateTime.now())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            var persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account id provided");
        }
        return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        if (eventStream.isEmpty()) {
            throw new IllegalStateException("Could not retrieve event stream from the event store");
        }

        return eventStream.stream().map(EventModel::getAggregateIdentifier).distinct().collect(Collectors.toList());
    }
}
