package com.clearpay.coin.controller;

import com.clearpay.coin.exceptions.NotFoundException;
import com.clearpay.coin.exceptions.TransferException;
import com.clearpay.coin.model.TransferRequest;
import com.clearpay.coin.model.User;
import com.clearpay.coin.services.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService service) {
        this.transferService = service;
    }


    @PostMapping
    public ResponseEntity<List<User>> doTransfer(@RequestBody @Valid TransferRequest request) {
        log.debug("Performing transfer between '{}' and '{}'", request.getSenderWalletId(), request.getRecipientWalletId());
        List<User> updatedUsers = transferService.transfer(request);
        return new ResponseEntity<>(updatedUsers, HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundHandler(NotFoundException ex) {
        log.debug("Returning not found response '{}'", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(Map.of(ex.getFieldName(), List.of(ex.getMessage()))), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<ErrorResponse> transferFailedHandler(TransferException ex) {
        log.debug("Returning 'bad request' response '{}'", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(Map.of(ex.getFieldName(), List.of(ex.getMessage()))), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.debug("Request did not passed validation '{}'", ex.getMessage());
        Map<String, List<String>> errors = new HashMap<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            List<String> fieldErrors = errors.getOrDefault(fieldName, new LinkedList<>());
            fieldErrors.add(error.getDefaultMessage());
            errors.put(fieldName, fieldErrors);
        }

        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
}
