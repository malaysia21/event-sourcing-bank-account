package com.aga.events.account.common.events;


import com.aga.events.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MoneyWithdrawEvent extends BaseEvent {

    private double amount;
}
