package com.aga.events.account.command.api.commands;

import com.aga.events.account.common.dto.AccountType;
import com.aga.events.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {

    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
