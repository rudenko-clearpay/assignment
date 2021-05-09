package com.clearpay.coin.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

@Data
@NoArgsConstructor
@Slf4j
public class Wallet {
  @Indexed
  private String id;
  private String balance;

  @PersistenceConstructor
  public Wallet(String id, String balance) {
    this.id = id != null ? id : UUID.randomUUID().toString();
    this.balance = balance;
  }
}
