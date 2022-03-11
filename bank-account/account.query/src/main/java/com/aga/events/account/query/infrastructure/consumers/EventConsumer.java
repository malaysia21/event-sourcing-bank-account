package com.aga.events.account.query.infrastructure.consumers;

import com.aga.events.account.common.events.AccountClosedEvent;
import com.aga.events.account.common.events.AccountOpenedEvent;
import com.aga.events.account.common.events.MoneyDepositedEvent;
import com.aga.events.account.common.events.MoneyWithdrawEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {

    void consumer(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consumer(@Payload AccountClosedEvent event, Acknowledgment ack);
    void consumer(@Payload MoneyDepositedEvent event, Acknowledgment ack);
    void consumer(@Payload MoneyWithdrawEvent event, Acknowledgment ack);
}
