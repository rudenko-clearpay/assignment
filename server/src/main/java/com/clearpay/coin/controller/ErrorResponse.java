package com.clearpay.coin.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    public static final String GENERAL_ERRORS = "formErrors";
    Map<String, List<String>> errors;
}
