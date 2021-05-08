package com.clearpay.coin.model;

import com.clearpay.coin.validation.StringRepresentsDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotBlank(message = "Origin user is required")
    String senderId;

    @NotBlank(message = "Origin wallet id is required")
    String senderWalletId;

    @NotBlank(message = "Destination wallet id is required")
    String recipientWalletId;

    @NotBlank(message = "Transfer amount is required")
    @StringRepresentsDecimal(message = "Need to provide a valid decimal number")
    String amount;
}
