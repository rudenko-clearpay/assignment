package com.clearpay.coin.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;


class PositiveDecimalValidatorTest {

   private final PositiveDecimalValidator validator = new PositiveDecimalValidator();

    @ParameterizedTest
    @ValueSource(strings = {"1", "1.1", "0", "0.0", "0.0000000001", "11111111111111111111111"})
    void numberAsStringAreValid(String value) {
        assertThat(validator.isValid(value, null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "ab", ".", ",", "/", "o"})
    void nonNumeralValuesAsStringAreNotValid(String value) {
        assertThat(validator.isValid(value, null)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "-0.1"})
    void negativeValuesAreNotValid(String value) {
        assertThat(validator.isValid(value, null)).isFalse();
    }
}