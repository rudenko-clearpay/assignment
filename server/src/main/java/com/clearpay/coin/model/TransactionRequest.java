package com.clearpay.coin.model;

import com.clearpay.coin.validation.PositiveDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotBlank(message = "Origin wallet id is required")
    String senderWalletId;

    @NotBlank(message = "Recipient wallet id is required")
    String recipientWalletId;

    @NotBlank(message = "Transfer amount is required")
    @PositiveDecimal
    String amount;
}
