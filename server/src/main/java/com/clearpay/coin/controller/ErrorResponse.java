package com.clearpay.coin.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    Map<String, List<String>> errors;
}
