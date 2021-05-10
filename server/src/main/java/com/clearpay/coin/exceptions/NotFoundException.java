package com.clearpay.coin.exceptions;

import com.clearpay.coin.model.TransferRequestField;

/*
* In the first place needed to let controller distinguish response code
* */
public class NotFoundException extends TransferException {
    public NotFoundException(String message, TransferRequestField field) {
        super(message, field);
    }
}