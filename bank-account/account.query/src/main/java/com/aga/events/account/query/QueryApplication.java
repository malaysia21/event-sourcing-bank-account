package com.aga.events.account.query;

import com.aga.events.account.query.api.queries.*;
import com.aga.events.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class QueryApplication {

	@Autowired
	QueryDispatcher queryDispatcher;

	@Autowired
	QueryHandler queryHandler;

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registerHandler(){
		queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handler);
		queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handler);
		queryDispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handler);
		queryDispatcher.registerHandler(FindAccountsWithBalanceQuery.class, queryHandler::handler);
	}

}
