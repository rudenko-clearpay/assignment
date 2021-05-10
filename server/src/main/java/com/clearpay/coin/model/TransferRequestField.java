package com.clearpay.coin.model;

/**
 * Mapping of transfer request fields. Used for better error responses to UI
 */
public enum TransferRequestField {
    SENDER("senderWalletId"), RECIPIENT("recipientWalletId"), AMOUNT("amount");

    private final String fieldName;

    TransferRequestField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
