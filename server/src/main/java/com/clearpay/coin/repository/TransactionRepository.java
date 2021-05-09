package com.clearpay.coin.repository;

import com.clearpay.coin.model.TransactionRequest;
import com.clearpay.coin.model.User;

import java.util.List;


public interface TransactionRepository {
    /**
     * Transfer coins between wallets
     * @throws com.clearpay.coin.exceptions.NotFoundException when any of entities is not found
     * @throws com.clearpay.coin.exceptions.LowBalanceException when sender doesn't have enough coins
     * @throws IllegalStateException when failed to obtain lock more that 4 times
     *
     * @param transactionRequest sender, recipient and amount
     * @return updated user entities
     */
    List<User> perform(TransactionRequest transactionRequest);
}
