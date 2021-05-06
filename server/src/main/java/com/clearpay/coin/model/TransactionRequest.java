package com.clearpay.coin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    String fromUser;
    String toUser;

    String fromWallet;
    String toWallet;

    long amount;
}
