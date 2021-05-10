package com.clearpay.coin.services.impl;

import com.clearpay.coin.exceptions.NotFoundException;
import com.clearpay.coin.exceptions.TransferException;
import com.clearpay.coin.model.TransferRequest;
import com.clearpay.coin.model.TransferRequestField;
import com.clearpay.coin.model.User;
import com.clearpay.coin.model.Wallet;
import com.clearpay.coin.repository.UserRepository;
import com.clearpay.coin.services.SupplierRetry;
import com.clearpay.coin.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {
    public static final int MAX_RETRIES = 3;
    private final UserRepository userRepository;
    private final SupplierRetry<List<User>> retryWrapper;

    @Autowired
    public TransferServiceImpl(UserRepository userRepository, SupplierRetry<List<User>> retryWrapper) {
        this.userRepository = userRepository;
        this.retryWrapper = retryWrapper;
    }

    @Override
    public List<User> transfer(TransferRequest transferRequest) {
        if(transferRequest.getRecipientWalletId().equals(transferRequest.getSenderWalletId())) {
            throw new TransferException("Should not be a different wallet", TransferRequestField.RECIPIENT);
        }

        return retryWrapper.supply(() -> {
            User sender = fetchUser(transferRequest.getSenderWalletId(), TransferRequestField.SENDER);
            Wallet senderWallet = getWallet(sender, transferRequest.getSenderWalletId(), TransferRequestField.SENDER);
            this.validateSenderBalance(senderWallet, transferRequest.getAmount());

            boolean isWalletsOwnerSameUser = sender.getWallets().stream().anyMatch(wallet -> wallet.getId().equals(transferRequest.getRecipientWalletId()));

            User recipient = isWalletsOwnerSameUser ? sender : fetchUser(transferRequest.getRecipientWalletId(), TransferRequestField.RECIPIENT);
            Wallet recipientWallet = getWallet(recipient, transferRequest.getRecipientWalletId(), TransferRequestField.RECIPIENT);

            this.transferCoins(senderWallet, recipientWallet, transferRequest.getAmount());
            return userRepository.saveAll(isWalletsOwnerSameUser ? List.of(sender) : List.of(sender, recipient));
        }, MAX_RETRIES);
    }

    private User fetchUser(String userId, TransferRequestField field) {
        return userRepository.findByWallets_Id(userId)
                .orElseThrow(() -> new NotFoundException("User with wallet '" + userId + "' was not found.", field));
    }

    private Wallet getWallet(User sender, String walletId, TransferRequestField field) {
        return sender.getWallets().stream()
                .filter(w -> w.getId().equals(walletId)).findFirst()
                .orElseThrow(() -> new NotFoundException("Wallet with id '" + walletId + "' was not found.", field));
    }


    private void transferCoins(Wallet from, Wallet to, String amount) {
        BigDecimal transferAmount = new BigDecimal(amount);
        BigDecimal newSenderBalance = new BigDecimal(from.getBalance()).subtract(transferAmount);

        String newRecipientBalance = new BigDecimal(to.getBalance()).add(transferAmount).stripTrailingZeros().toPlainString();

        from.setBalance(newSenderBalance.stripTrailingZeros().toPlainString());
        to.setBalance(newRecipientBalance);
    }

    private void validateSenderBalance(Wallet sourceWallet, String amount) {
        BigDecimal transferAmount = new BigDecimal(amount);
        BigDecimal newSenderBalance = new BigDecimal(sourceWallet.getBalance()).subtract(transferAmount);

        if (newSenderBalance.floatValue() < 0) {
            throw new TransferException("Wallet '" + sourceWallet.getId() + "' has insufficient balance for the transfer.", TransferRequestField.AMOUNT);
        }
    }
}
