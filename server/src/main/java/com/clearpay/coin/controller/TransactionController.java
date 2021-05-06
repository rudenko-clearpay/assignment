package com.clearpay.coin.controller;

import com.clearpay.coin.model.TransactionRequest;
import com.clearpay.coin.model.User;
import com.clearpay.coin.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

  final TransactionRepository repository;

  @Autowired
  public TransactionController(TransactionRepository repository) {
    this.repository = repository;
  }


  @PostMapping()
  public ResponseEntity<List<User>> doTransaction(@RequestBody TransactionRequest request) {
    List<User> updatedUsers = repository.perform(request);
    return new ResponseEntity<>(updatedUsers, HttpStatus.OK);
  }
}
