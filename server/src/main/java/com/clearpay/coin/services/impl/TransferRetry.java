package com.clearpay.coin.services.impl;

import com.clearpay.coin.model.User;
import com.clearpay.coin.services.SupplierRetry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

/**
 * Retries Persistence operation that potentially could fail because of Optimistic Lock.
 */
@Slf4j
@Component
public class TransferRetry implements SupplierRetry<List<User>> {

    @Override
    public List<User> supply(Supplier<List<User>> codeToRetry, Integer maxTries) {
        return supply(codeToRetry, maxTries, 1);
    }

    public List<User> supply(Supplier<List<User>> codeToRetry, Integer maxTries, Integer tryN) {
        try {
            return codeToRetry.get();
        } catch (OptimisticLockingFailureException e) {
            log.info("Failed to update, trying again. Retry #{}", tryN + 1);
            if (tryN < maxTries) {
                List<User> result = supply(codeToRetry, maxTries, tryN + 1);
                log.info("Finally performed transfer from {} try.", tryN + 1);
                return result;
            } else {
                throw new IllegalStateException("Failed to update. Can't retrieve lock");
            }
        }
    }
}
