package com.clearpay.coin.repository;

import com.clearpay.coin.exceptions.LowBalanceException;
import com.clearpay.coin.exceptions.NotFoundException;
import com.clearpay.coin.model.TransactionRequest;
import com.clearpay.coin.model.User;
import com.clearpay.coin.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Slf4j
public class TransactionRepositoryImpl implements TransactionRepository {
    public static final int MAX_RETRIES = 3;
    private final UserRepository userRepository;

    public TransactionRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> perform(TransactionRequest transactionRequest) {
        if (transactionRequest.getRecipientWalletId().equals(transactionRequest.getSenderWalletId())) {
            return List.of();
        }
        return perform(transactionRequest, 0);
    }

    public List<User> perform(TransactionRequest transactionRequest, int retry) {
        User fromUser = findUserByWalletId(transactionRequest.getSenderWalletId());
        Wallet senderWallet = findWallet(fromUser, transactionRequest.getSenderWalletId());
        BigDecimal transferAmount = new BigDecimal(transactionRequest.getAmount());
        BigDecimal newSenderBalance = new BigDecimal(senderWallet.getBalance()).subtract(transferAmount);

        if (newSenderBalance.floatValue() < 0) {
            throw new LowBalanceException("Wallet '" + transactionRequest.getSenderWalletId() + "' has insufficient balance for the transaction.");
        }

        boolean isWalletsOwnerSameUser = fromUser.getWallets().stream().anyMatch(wallet -> wallet.getId().equals(transactionRequest.getRecipientWalletId()));
        User toUser = isWalletsOwnerSameUser ? fromUser : findUserByWalletId(transactionRequest.getRecipientWalletId());

        Wallet recipientWallet = findWallet(toUser, transactionRequest.getRecipientWalletId());
        String newRecipientBalance = new BigDecimal(recipientWallet.getBalance()).add(transferAmount).stripTrailingZeros().toPlainString();

        senderWallet.setBalance(newSenderBalance.stripTrailingZeros().toPlainString());
        recipientWallet.setBalance(newRecipientBalance);

        try {
            return userRepository.saveAll(isWalletsOwnerSameUser ? List.of(fromUser) : List.of(fromUser, toUser));
        } catch (OptimisticLockingFailureException e) {
            log.info("Failed to update, trying again. Retry #{}", retry + 1);
            if (retry < MAX_RETRIES) {
                List<User> list = perform(transactionRequest, retry + 1);
                log.info("Finally performed transaction from {} try.", retry + 1);
                return list;
            } else {
                throw new IllegalStateException("Failed to update. Can't retrieve lock");
            }
        }
    }

    private Wallet findWallet(User user, String walletId) {
        return user.getWallets().stream()
                .filter(w -> w.getId().equals(walletId)).findFirst()
                .orElseThrow(() -> new NotFoundException("Wallet with id '" + walletId + "' was not found."));
    }

    private User findUserByWalletId(String walletId) {
        return userRepository.findByWallets_Id(walletId).stream().findFirst()
                .orElseThrow(() -> new NotFoundException("User with wallet '" + walletId + "' was not found."));
    }
}
