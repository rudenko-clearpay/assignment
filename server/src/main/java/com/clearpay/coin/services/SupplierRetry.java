package com.clearpay.coin.services;

import java.util.function.Supplier;

public interface SupplierRetry<T> {
    T supply(Supplier<T> codeToRetry, Integer times);
}
