package com.clearpay.coin.services;

import com.clearpay.coin.model.TransferRequest;
import com.clearpay.coin.model.User;

import java.util.List;

public interface TransferService {
    /**
     * Transfer coins between wallets
     * @throws com.clearpay.coin.exceptions.TransferException when any generic issue occurs
     * @throws com.clearpay.coin.exceptions.NotFoundException when any entity wasn't found
     * @throws IllegalStateException when failed to obtain lock more that 4 times
     *
     * @param transferRequest sender, recipient and amount
     * @return updated user entities
     */
    List<User> transfer(TransferRequest transferRequest);
}
