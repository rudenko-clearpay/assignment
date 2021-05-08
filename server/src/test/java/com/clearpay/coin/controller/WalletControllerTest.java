package com.clearpay.coin.controller;

import com.clearpay.coin.model.User;
import com.clearpay.coin.model.Wallet;
import com.clearpay.coin.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WalletControllerTest {

    public static final String USER_ID = "USER_ID_1";
    @LocalServerPort
    private int port;

    @MockBean
    private UserRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;
    private String URL;

    private final Wallet wallet11 = new Wallet("WALLET_U1_1", "99");
    private final Wallet wallet12 = new Wallet("WALLET_U1_2", "2000");
    private final User user1 = new User(USER_ID, "Name1", List.of(wallet11, wallet12));

    @BeforeEach
    void setUp() {
        URL = "http://localhost:" + port + "/wallets/" + USER_ID;
    }

    @Test
    @DisplayName("Should return wallets list when user was found")
    public void successTransactionResponse() throws JsonProcessingException {
        List<Wallet> expected = List.of(wallet11, wallet12);
        when(repository.findById(any())).thenReturn(Optional.of(user1));
        String result = this.restTemplate.getForObject(URL, String.class);

        assertThat(result).isEqualTo(new ObjectMapper().writeValueAsString(expected));
    }

    @Test
    @DisplayName("Should return 404 status when user wasn't found")
    public void notFoundResponse() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        ResponseEntity<String> result = this.restTemplate.getForEntity(URL, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}