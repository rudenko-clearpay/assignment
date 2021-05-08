package com.clearpay.coin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  private String id;
  private String fullName;
  private List<Wallet> wallets;

  public User(String id, String fullName, List<Wallet> wallets) {
    this.id = id;
    this.fullName = fullName;
    this.wallets = wallets;
  }

  @Version
  @RestResource(exported = false)
  private long version;
}
