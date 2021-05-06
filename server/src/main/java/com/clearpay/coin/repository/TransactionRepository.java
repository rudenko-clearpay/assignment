package com.clearpay.coin.repository;

import com.clearpay.coin.model.TransactionRequest;
import com.clearpay.coin.model.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TransactionRepository {

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    List<User> perform(TransactionRequest transactionRequest);
}
