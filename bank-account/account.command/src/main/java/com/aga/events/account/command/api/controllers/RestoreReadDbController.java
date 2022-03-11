package com.aga.events.account.command.api.controllers;

import com.aga.events.account.command.api.commands.RestoreReadDbCommand;
import com.aga.events.account.common.dto.BaseResponse;
import com.aga.events.cqrs.core.exceptions.AggregateNotFoundException;
import com.aga.events.cqrs.core.infrastructure.CommandDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/restore-db")
@AllArgsConstructor
public class RestoreReadDbController {

    private static final Logger logger = Logger.getLogger(RestoreReadDbController.class.getName());
    private final CommandDispatcher commandDispatcher;

    @GetMapping()
    ResponseEntity<BaseResponse> restoreDb() {
        try {
            commandDispatcher.send(new RestoreReadDbCommand());
            return new ResponseEntity<>(new BaseResponse("Read database restore request completed successfully"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("Bad request:  {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to restore database";
            logger.log(Level.SEVERE, errorMessage, e);
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
