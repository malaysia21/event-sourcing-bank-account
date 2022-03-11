package com.aga.events.account.command.infrastructure;

import com.aga.events.cqrs.core.events.BaseEvent;
import com.aga.events.cqrs.core.produces.EventProducer;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountEventProducer implements EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        this.kafkaTemplate.send(topic, event);
    }
}
