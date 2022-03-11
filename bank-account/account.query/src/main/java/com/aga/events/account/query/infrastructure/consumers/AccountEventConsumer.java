package com.aga.events.account.query.infrastructure.consumers;

import com.aga.events.account.common.events.AccountClosedEvent;
import com.aga.events.account.common.events.AccountOpenedEvent;
import com.aga.events.account.common.events.MoneyDepositedEvent;
import com.aga.events.account.common.events.MoneyWithdrawEvent;
import com.aga.events.account.query.infrastructure.handlers.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountEventConsumer  implements EventConsumer{

    private final EventHandler eventHandler;

    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consumer(AccountOpenedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consumer(AccountClosedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "MoneyDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consumer(MoneyDepositedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "MoneyWithdrawEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consumer(MoneyWithdrawEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }
}
