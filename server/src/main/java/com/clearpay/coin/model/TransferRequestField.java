package com.clearpay.coin.model;

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
