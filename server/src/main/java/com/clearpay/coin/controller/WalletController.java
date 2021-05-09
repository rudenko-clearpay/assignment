package com.clearpay.coin.controller;

import com.clearpay.coin.exceptions.NotFoundException;
import com.clearpay.coin.model.User;
import com.clearpay.coin.model.Wallet;
import com.clearpay.coin.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.clearpay.coin.controller.ErrorResponse.GENERAL_ERRORS;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/wallets", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class WalletController {

    final UserRepository repository;

    @Autowired
    public WalletController(UserRepository repository) {
        this.repository = repository;
    }


    @GetMapping(path = "/{userId}")
    public ResponseEntity<List<Wallet>> getUserWallets(@PathVariable String userId) {
        log.info("Retrieving user wallets '{}'", userId);
        Optional<User> user = repository.findById(userId);

        return user.map(value -> new ResponseEntity<>(value.getWallets(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundHandler(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(Map.of(GENERAL_ERRORS, List.of(ex.getMessage()))), HttpStatus.NOT_FOUND);
    }
}
