package com.clearpay.coin.repository;

import com.clearpay.coin.exceptions.LowBalanceException;
import com.clearpay.coin.exceptions.NotFoundException;
import com.clearpay.coin.model.TransactionRequest;
import com.clearpay.coin.model.User;
import com.clearpay.coin.model.Wallet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    private final UserRepository userRepository;

    public TransactionRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<User> perform(TransactionRequest transactionRequest) {
        User fromUser = findUser(transactionRequest.getFromUser());
        Wallet walletFrom = findWallet(fromUser, transactionRequest.getFromWallet());

        if (walletFrom.getBalance() - transactionRequest.getAmount() < 0) {
            throw new LowBalanceException("Wallet '" + transactionRequest.getFromWallet() + "' has insufficient balance for the transaction.");
        }

        boolean isSameUser = transactionRequest.getFromUser().equals(transactionRequest.getToUser());
        User toUser = isSameUser ? fromUser : findUser(transactionRequest.getToUser());
        Wallet walletTo = findWallet(toUser, transactionRequest.getToWallet());

        walletFrom.setBalance(walletFrom.getBalance() - transactionRequest.getAmount());
        walletTo.setBalance(walletTo.getBalance() + transactionRequest.getAmount());

        return userRepository.saveAll(isSameUser ? List.of(fromUser) : List.of(fromUser, toUser));
    }

    private Wallet findWallet(User user, String walletId) {
        return user.getWallets().stream().filter(w -> w.getId().equals(walletId)).findFirst()
                .orElseThrow(() -> new NotFoundException("Wallet with id " + walletId + " was not found."));
    }

    private User findUser(String user) {
        return userRepository.findById(user)
                .orElseThrow(() -> new NotFoundException("User with id " + user + " was not found."));
    }
}
