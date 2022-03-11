package com.aga.events.account.command.api.commands;


import com.aga.events.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawMoneyCommand extends BaseCommand {
    private double amount;
}
