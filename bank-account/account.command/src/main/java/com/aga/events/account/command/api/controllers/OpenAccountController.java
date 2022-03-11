package com.aga.events.account.command.api.controllers;

import com.aga.events.account.command.api.commands.OpenAccountCommand;
import com.aga.events.account.command.api.dto.OpenAccountResponse;
import com.aga.events.account.common.dto.BaseResponse;
import com.aga.events.cqrs.core.infrastructure.CommandDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/bank/account/open")
@AllArgsConstructor
public class OpenAccountController {

    private static final Logger logger = Logger.getLogger(OpenAccountController.class.getName());
    private final CommandDispatcher commandDispatcher;

    @PostMapping
    ResponseEntity<BaseResponse> openBankAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);

        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Bank account created", id), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Bad request:  {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var errorMessage = MessageFormat.format("Error while processing request for new bank account id: {0}", id);
            logger.log(Level.SEVERE, errorMessage, e);
            return new ResponseEntity<>(new OpenAccountResponse(errorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
