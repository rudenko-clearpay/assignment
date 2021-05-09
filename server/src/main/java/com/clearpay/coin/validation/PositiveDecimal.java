package com.clearpay.coin.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PositiveDecimalValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveDecimal {
    String message() default "Must be a provide a positive decimal number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
