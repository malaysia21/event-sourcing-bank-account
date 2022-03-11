package com.aga.events.account.command.api.controllers;

import com.aga.events.account.command.api.commands.CloseAccountCommand;
import com.aga.events.account.common.dto.BaseResponse;
import com.aga.events.cqrs.core.exceptions.AggregateNotFoundException;
import com.aga.events.cqrs.core.infrastructure.CommandDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/bank/account/close")
@AllArgsConstructor
public class CloseAccountController {

    private static final Logger logger = Logger.getLogger(CloseAccountController.class.getName());
    private final CommandDispatcher commandDispatcher;

    @DeleteMapping(path = "/{id}")
    ResponseEntity<BaseResponse> closeBankAccount(@PathVariable(value = "id") String id) {
        try {
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse("Bank account closed successfully"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("Bad request:  {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var errorMessage = MessageFormat.format("Error while processing request to close bank account id: {0}", id);
            logger.log(Level.SEVERE, errorMessage, e);
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
