package com.clearpay.coin.controller;

import com.clearpay.coin.model.TransactionRequest;
import com.clearpay.coin.model.User;
import com.clearpay.coin.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private TransactionRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;
    private String URL;
    private final TransactionRequest transactionRequest = new TransactionRequest();

    @BeforeEach
    void setUp() {
        URL = "http://localhost:" + port + "/transaction";
        when(repository.perform(any())).thenReturn(List.of(new User()));
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws JsonProcessingException {
        List<User> expected = List.of(new User());

        when(repository.perform(transactionRequest)).thenReturn(expected);
        String result = this.restTemplate.postForObject(URL, transactionRequest, String.class);

        assertThat(result).isEqualTo(new ObjectMapper().writeValueAsString(expected));
    }
}