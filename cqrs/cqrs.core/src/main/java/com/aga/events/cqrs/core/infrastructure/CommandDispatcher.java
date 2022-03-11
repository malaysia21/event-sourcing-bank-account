package com.aga.events.cqrs.core.infrastructure;

import com.aga.events.cqrs.core.commands.BaseCommand;
import com.aga.events.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
