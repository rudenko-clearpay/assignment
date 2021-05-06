package com.clearpay.coin.exceptions;

public class LowBalanceException extends RuntimeException {
    public LowBalanceException(String s) {
        super(s);
    }
}
