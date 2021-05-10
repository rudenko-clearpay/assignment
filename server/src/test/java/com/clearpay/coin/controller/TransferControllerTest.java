package com.clearpay.coin.controller;

import com.clearpay.coin.exceptions.NotFoundException;
import com.clearpay.coin.exceptions.TransferException;
import com.clearpay.coin.model.TransferRequest;
import com.clearpay.coin.model.User;
import com.clearpay.coin.repository.UserRepository;
import com.clearpay.coin.services.TransferService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.Map;

import static com.clearpay.coin.model.TransferRequestField.AMOUNT;
import static com.clearpay.coin.model.TransferRequestField.RECIPIENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private TransferService repository;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;
    private String URL;
    private final TransferRequest transferRequest = new TransferRequest("B", "C", "1000");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        URL = "http://localhost:" + port + "/transfer";
        when(repository.transfer(any())).thenReturn(List.of(new User()));
    }

    @Test
    @DisplayName("Success transfer should return updated user entities")
    public void successTransferResponse() throws JsonProcessingException {
        List<User> expected = List.of(new User());

        when(repository.transfer(transferRequest)).thenReturn(expected);
        String result = this.restTemplate.postForObject(URL, transferRequest, String.class);

        assertThat(result).isEqualTo(new ObjectMapper().writeValueAsString(expected));
    }

    @Test
    @DisplayName("Controller should validate fields")
    public void missingFieldRequest() throws JsonProcessingException {
        Map<String, List<String>> expected = Map.of("recipientWalletId", List.of("Recipient wallet id is required"));
        transferRequest.setRecipientWalletId(null);
        String result = this.restTemplate.postForObject(URL, transferRequest, String.class);

        assertThat(result).isEqualTo(new ObjectMapper().writeValueAsString(new ErrorResponse(expected)));
    }

    @Test
    @DisplayName("Controller should validate amount type")
    public void wrongFieldType() throws JsonProcessingException {
        Map<String, List<String>> expected = Map.of("amount", List.of("Must be a positive decimal number"));
        transferRequest.setAmount("not a number");
        String result = this.restTemplate.postForObject(URL, transferRequest, String.class);

        assertThat(result).isEqualTo(new ObjectMapper().writeValueAsString(new ErrorResponse(expected)));
    }

    @Test
    @DisplayName("Controller should process low balance exception")
    public void controllerCatchesInsufficientBalanceError() throws JsonProcessingException {
        Map<String, List<String>> expected = Map.of(AMOUNT.getFieldName(), List.of("msg"));

        when(repository.transfer(transferRequest)).thenThrow(new TransferException("msg", AMOUNT));
        String result = this.restTemplate.postForObject(URL, transferRequest, String.class);

        assertThat(result).isEqualTo(new ObjectMapper().writeValueAsString(new ErrorResponse(expected)));
    }

    @Test
    @DisplayName("Controller should process not found exception")
    public void controllerCatchesNotFoundError() throws JsonProcessingException {
        Map<String, List<String>> expected = Map.of(RECIPIENT.getFieldName(), List.of("msg"));


        when(repository.transfer(transferRequest)).thenThrow(new NotFoundException("msg", RECIPIENT));
        String result = this.restTemplate.postForObject(URL, transferRequest, String.class);

        assertThat(result).isEqualTo(new ObjectMapper().writeValueAsString(new ErrorResponse(expected)));
    }
}