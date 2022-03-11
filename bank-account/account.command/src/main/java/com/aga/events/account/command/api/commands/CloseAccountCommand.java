package com.aga.events.account.command.api.commands;

import com.aga.events.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class CloseAccountCommand extends BaseCommand {

    public CloseAccountCommand(String id) {
        super(id);
    }
}
