package com.clearpay.coin.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class StringRepresentsDecimalValidator implements ConstraintValidator<StringRepresentsDecimal, String> {

    @Override
    public void initialize(StringRepresentsDecimal contactNumber) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        try {
            new BigDecimal(field);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
