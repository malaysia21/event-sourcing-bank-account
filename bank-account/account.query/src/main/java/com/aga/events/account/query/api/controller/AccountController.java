package com.aga.events.account.query.api.controller;

import com.aga.events.account.query.api.queries.FindAccountByHolderQuery;
import com.aga.events.account.query.api.queries.FindAccountByIdQuery;
import com.aga.events.account.query.api.queries.FindAccountsWithBalanceQuery;
import com.aga.events.account.query.api.queries.FindAllAccountsQuery;
import com.aga.events.account.query.api.queries.dto.AccountResponse;
import com.aga.events.account.query.api.queries.dto.EqualityType;
import com.aga.events.account.query.domain.BankAccount;
import com.aga.events.cqrs.core.infrastructure.QueryDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/accounts")
@AllArgsConstructor
public class AccountController {

    private final Logger logger = Logger.getLogger(AccountController.class.getName());
    private final QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountResponse> getAllAccounts(){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());

            if(accounts == null || accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank accounts", accounts.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            var message = "Failed to complete get all accounts request";
            return new ResponseEntity<>(new AccountResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable(value = "id") String id){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));

            if(accounts == null || accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank account")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            var message = "Failed to complete get account by id request";
            return new ResponseEntity<>(new AccountResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/holder/{accountHolder}")
    public ResponseEntity<AccountResponse> getAccountByAccountHolder(@PathVariable(value = "accountHolder") String accountHolder){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));

            if(accounts == null || accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank account")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            var message = "Failed to complete get account by holder request";
            return new ResponseEntity<>(new AccountResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/balance/{equalityType}/{balance}")
    public ResponseEntity<AccountResponse> getAccountsWithBalance(@PathVariable(value = "equalityType") EqualityType equalityType,
                                                                  @PathVariable(value = "balance") double balance) {
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountsWithBalanceQuery(equalityType, balance));

            if(accounts == null || accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            var response = AccountResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank accounts", accounts.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            var message = "Failed to complete get accounts with balance request";
            return new ResponseEntity<>(new AccountResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
