package com.clearpay.coin.exceptions;

import com.clearpay.coin.model.TransferRequestField;

/*
* Any expected problem that is uses fault may cause this.
* Is supposed to result as BAD_REQUEST status on UI
* */
public class TransferException extends RuntimeException {
    private final String fieldName;

    public TransferException(String message, TransferRequestField field) {
        super(message);
        this.fieldName = field.getFieldName();
    }

    public String getFieldName() {
        return this.fieldName;
    }
}
