package com.clearpay.coin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
  @Id
  private String id = UUID.randomUUID().toString();
  private long balance;
}
