package com.aga.events.cqrs.core.produces;

import com.aga.events.cqrs.core.events.BaseEvent;

public interface EventProducer {

    void produce(String topic, BaseEvent event);
}
