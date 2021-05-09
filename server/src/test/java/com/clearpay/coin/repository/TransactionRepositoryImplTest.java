package com.clearpay.coin.repository;

import com.clearpay.coin.exceptions.LowBalanceException;
import com.clearpay.coin.exceptions.NotFoundException;
import com.clearpay.coin.model.TransactionRequest;
import com.clearpay.coin.model.User;
import com.clearpay.coin.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class TransactionRepositoryImplTest {

    public static final String USER_ID_1 = "ID1";
    public static final String USER_ID_2 = "ID2";
    public static final String WALLET_U1_2 = "wallet12";
    public static final String WALLET_U2_2 = "wallet22";
    public static final String WALLET_U1_1 = "wallet11";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionRepositoryImpl transactionRepository;

    private final Wallet wallet11 = new Wallet(WALLET_U1_1, "99");
    private final Wallet wallet12 = new Wallet(WALLET_U1_2, "2000");
    private final User user1 = new User(USER_ID_1, "Name1", List.of(wallet11, wallet12));

    private final Wallet wallet21 = new Wallet("wallet21", "0");
    private final Wallet wallet22 = new Wallet(WALLET_U2_2, "0");
    private final User user2 = new User(USER_ID_2, "Name1", List.of(wallet21, wallet22));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Transaction passes fine")
    void testTransactionOK() {
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user1));
        when(userRepository.findByWallets_Id(WALLET_U2_2)).thenReturn(List.of(user2));

        transactionRepository.perform(new TransactionRequest(USER_ID_1, WALLET_U1_2, WALLET_U2_2, "100"));
        assertThat(user1.getWallets().get(1).getBalance()).isEqualTo("1900");
        assertThat(user2.getWallets().get(1).getBalance()).isEqualTo("100");
        verify(userRepository).saveAll(List.of(user1, user2));
    }

    @Test
    @DisplayName("Transaction between wallets of the same user passes fine")
    void testTransactionWithinSameUser() {
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user1));

        transactionRepository.perform(new TransactionRequest(USER_ID_1, WALLET_U1_2, WALLET_U1_1, "100"));

        assertThat(user1.getWallets().get(0).getBalance()).isEqualTo("199");
        assertThat(user1.getWallets().get(1).getBalance()).isEqualTo("1900");
        verify(userRepository).saveAll(List.of(user1));
        verify(userRepository, times(1)).findById(USER_ID_1);
    }

    @Test
    @DisplayName("Transaction fails due to low balance")
    void testTransactionLowBalance() {
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(USER_ID_2)).thenReturn(Optional.of(user2));


        Executable operation = () -> transactionRepository.perform(new TransactionRequest(USER_ID_1, WALLET_U1_1, WALLET_U2_2, "100"));
        LowBalanceException exception = assertThrows(LowBalanceException.class,  operation);

        assertThat(exception.getMessage()).isEqualTo("Wallet '" + WALLET_U1_1 + "' has insufficient balance for the transaction.");
    }

    @Test
    @DisplayName("Throws exception when origin user not found")
    void testTransactionOriginUserNotFound() {
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.empty());
        when(userRepository.findById(USER_ID_2)).thenReturn(Optional.of(user2));

        Executable operation = () -> transactionRepository.perform(new TransactionRequest(USER_ID_1, WALLET_U1_2, WALLET_U2_2, "100"));
        NotFoundException exception = assertThrows(NotFoundException.class,  operation);

        assertThat(exception.getMessage()).isEqualTo("User with id '" + USER_ID_1 + "' was not found.");
    }

    @Test
    @DisplayName("Throws exception when origin wallet not found")
    void testTransactionOriginWalletNotFound() {
        user1.setWallets(List.of(wallet11));
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user1));

        Executable operation = () -> transactionRepository.perform(new TransactionRequest(USER_ID_1, WALLET_U1_2, WALLET_U2_2, "100"));
        NotFoundException exception = assertThrows(NotFoundException.class,  operation);

        assertThat(exception.getMessage()).isEqualTo("Wallet with id '" + WALLET_U1_2 + "' was not found.");
    }

    @Test
    @DisplayName("Throws exception when destination wallet not found")
    void testTransactionDestinationWalletNotFound() {
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user1));
        user2.setWallets(List.of(wallet21));
        when(userRepository.findById(USER_ID_2)).thenReturn(Optional.of(user2));
        Executable operation = () -> transactionRepository.perform(new TransactionRequest(USER_ID_1, WALLET_U1_2, WALLET_U2_2, "100"));
        NotFoundException exception = assertThrows(NotFoundException.class,  operation);

        assertThat(exception.getMessage()).isEqualTo("User with wallet '" + WALLET_U2_2 + "' was not found.");
    }

    @Test
    @DisplayName("Throws exception when destination user not found")
    void testTransactionDestinationUserNotFound() {
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(USER_ID_2)).thenReturn(Optional.empty());

        Executable operation = () -> transactionRepository.perform(new TransactionRequest(USER_ID_1, WALLET_U1_2, WALLET_U2_2, "100"));
        NotFoundException exception = assertThrows(NotFoundException.class,  operation);

        assertThat(exception.getMessage()).isEqualTo("User with wallet '" + WALLET_U2_2 + "' was not found.");
    }
}