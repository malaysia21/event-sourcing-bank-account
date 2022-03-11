package com.aga.events.account.command.api.controllers;

import com.aga.events.account.command.api.commands.WithdrawMoneyCommand;
import com.aga.events.account.common.dto.BaseResponse;
import com.aga.events.cqrs.core.infrastructure.CommandDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/bank/account/withdraw")
@AllArgsConstructor
public class WithdrawMoneyController {

    private static final Logger logger = Logger.getLogger(WithdrawMoneyController.class.getName());
    private final CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    ResponseEntity<BaseResponse> withdrawMoney(@PathVariable(value = "id") String id,
                                              @RequestBody WithdrawMoneyCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Withdraw money completed successfully"), HttpStatus.OK);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Bad request:  {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var errorMessage = MessageFormat.format("Error while processing request for withdraw money for account id: {}", id);
            logger.log(Level.SEVERE, errorMessage, e);
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
