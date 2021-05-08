package com.clearpay.coin.controller;

import com.clearpay.coin.exceptions.LowBalanceException;
import com.clearpay.coin.exceptions.NotFoundException;
import com.clearpay.coin.model.TransactionRequest;
import com.clearpay.coin.model.User;
import com.clearpay.coin.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
import java.util.stream.Collectors;

import static com.clearpay.coin.controller.ErrorResponse.GENERAL_ERRORS;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    final TransactionRepository repository;

    @Autowired
    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }


    @PostMapping
    public ResponseEntity<List<User>> doTransaction(@RequestBody @Valid TransactionRequest request) {
        List<User> updatedUsers = repository.perform(request);
        return new ResponseEntity<>(updatedUsers, HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundHandler(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(Map.of(GENERAL_ERRORS, List.of(ex.getMessage()))), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LowBalanceException.class)
    public ResponseEntity<ErrorResponse> lowBalanceHandler(LowBalanceException ex) {
        return new ResponseEntity<>(new ErrorResponse(Map.of(GENERAL_ERRORS, List.of(ex.getMessage()))), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
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
