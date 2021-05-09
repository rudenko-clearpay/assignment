package com.clearpay.coin.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class PositiveDecimalValidator implements ConstraintValidator<PositiveDecimal, String> {

    @Override
    public void initialize(PositiveDecimal contactNumber) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        try {
            BigDecimal input = new BigDecimal(field);
            return isGreaterThanZero(input);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isGreaterThanZero(BigDecimal input) {
        return input.compareTo(new BigDecimal(0)) >= 0;
    }
}
