package com.aga.events.account.query.infrastructure.handlers;

import com.aga.events.account.common.events.AccountClosedEvent;
import com.aga.events.account.common.events.AccountOpenedEvent;
import com.aga.events.account.common.events.MoneyDepositedEvent;
import com.aga.events.account.common.events.MoneyWithdrawEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);
    void on(AccountClosedEvent event);
    void on(MoneyDepositedEvent event);
    void on(MoneyWithdrawEvent event);
}
