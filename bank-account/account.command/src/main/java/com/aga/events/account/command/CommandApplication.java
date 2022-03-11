package com.aga.events.account.command;

import com.aga.events.account.command.api.commands.*;
import com.aga.events.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommandApplication {

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}


	@PostConstruct
	public void registerHandlers(){
		commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(DepositMoneyCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(WithdrawMoneyCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(RestoreReadDbCommand.class, commandHandler::handle);
	}
}
