package com.clearpay.coin.services;

import java.util.function.Supplier;

public interface SupplierRetry<T> {
    /**
     * @param codeToRetry this supplier will be executed at most N times
     * @param times how many retries to do until throw IllegalStateException
     * @return supplied value
     */
    T supply(Supplier<T> codeToRetry, Integer times);
}
