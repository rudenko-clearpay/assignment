package com.clearpay.coin.repository;

import com.clearpay.coin.exceptions.TransferException;
import com.clearpay.coin.model.TransferRequest;
import com.clearpay.coin.model.User;
import com.clearpay.coin.model.Wallet;
import com.clearpay.coin.services.impl.TransferServiceImpl;
import com.clearpay.coin.services.impl.TransferRetry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.List;
import java.util.Optional;

import static com.clearpay.coin.services.impl.TransferServiceImpl.MAX_RETRIES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class TransferRepositoryImplTest {

    public static final String USER_ID_1 = "ID1";
    public static final String USER_ID_2 = "ID2";
    public static final String WALLET_U1_2 = "wallet12";
    public static final String WALLET_U2_2 = "wallet22";
    public static final String WALLET_U1_1 = "wallet11";

    @Mock
    private UserRepository userRepository;

    private final TransferRetry wrapper = new TransferRetry();
    private TransferServiceImpl transferService;

    private final Wallet wallet11 = new Wallet(WALLET_U1_1, "99");
    private final Wallet wallet12 = new Wallet(WALLET_U1_2, "2000");
    private final User user1 = new User(USER_ID_1, "Name1", List.of(wallet11, wallet12));

    private final Wallet wallet21 = new Wallet("wallet21", "0");
    private final Wallet wallet22 = new Wallet(WALLET_U2_2, "0");
    private final User user2 = new User(USER_ID_2, "Name1", List.of(wallet21, wallet22));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferService = new TransferServiceImpl(userRepository, wrapper);
        when(userRepository.findByWallets_Id(WALLET_U1_1)).thenReturn(Optional.of(user1));
        when(userRepository.findByWallets_Id(WALLET_U1_2)).thenReturn(Optional.of(user1));
        when(userRepository.findByWallets_Id(WALLET_U2_2)).thenReturn(Optional.of(user2));
    }

    @Test
    @DisplayName("Transfer passes fine")
    void testTransferOK() {
        transferService.transfer(new TransferRequest(WALLET_U1_2, WALLET_U2_2, "100"));
        assertThat(user1.getWallets().get(1).getBalance()).isEqualTo("1900");
        assertThat(user2.getWallets().get(1).getBalance()).isEqualTo("100");
        verify(userRepository).saveAll(List.of(user1, user2));
    }

    @Test
    @DisplayName("Transfer between wallets of the same user passes fine")
    void testTransferWithinSameUser() {
        when(userRepository.findByWallets_Id(WALLET_U1_2)).thenReturn(Optional.of(user1));

        transferService.transfer(new TransferRequest(WALLET_U1_2, WALLET_U1_1, "100"));

        assertThat(user1.getWallets().get(0).getBalance()).isEqualTo("199");
        assertThat(user1.getWallets().get(1).getBalance()).isEqualTo("1900");
        verify(userRepository).saveAll(List.of(user1));
        verify(userRepository, times(1)).findByWallets_Id(any());
    }
    @Test
    @DisplayName("Transfer between same wallets does nothing")
    void testTransferWithinSameWalletDoesNothing() {
        when(userRepository.findByWallets_Id(WALLET_U1_1)).thenReturn(Optional.of(user1));

        Executable operation = () -> transferService.transfer(new TransferRequest(WALLET_U1_1, WALLET_U1_1, "1"));
        TransferException exception = assertThrows(TransferException.class, operation);

        assertThat(exception.getMessage()).isEqualTo("Should not be a different wallet");

        assertThat(user1.getWallets().get(0).getBalance()).isEqualTo("99");
        assertThat(user1.getWallets().get(1).getBalance()).isEqualTo("2000");

        verify(userRepository, never()).saveAll(List.of(user1));
        verify(userRepository, never()).findByWallets_Id(any());
    }

    @Test
    @DisplayName("Transfer fails due to low balance")
    void testTransferLowBalance() {
        when(userRepository.findById(WALLET_U1_1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(WALLET_U2_2)).thenReturn(Optional.of(user2));


        Executable operation = () -> transferService.transfer(new TransferRequest(WALLET_U1_1, WALLET_U2_2, "100"));
        TransferException exception = assertThrows(TransferException.class, operation);

        assertThat(exception.getMessage()).isEqualTo("Wallet '" + WALLET_U1_1 + "' has insufficient balance for the transfer.");
    }

    @Test
    @DisplayName("Throws exception when origin user not found")
    void testTransferOriginUserNotFound() {
        when(userRepository.findByWallets_Id(WALLET_U1_2)).thenReturn(Optional.empty());

        Executable operation = () -> transferService.transfer(new TransferRequest(WALLET_U1_2, WALLET_U2_2, "100"));
        TransferException exception = assertThrows(TransferException.class, operation);

        assertThat(exception.getMessage()).isEqualTo("User with wallet '" + WALLET_U1_2 + "' was not found.");
    }

    @Test
    @DisplayName("Throws exception when destination wallet not found")
    void testTransferDestinationWalletNotFound() {
        when(userRepository.findByWallets_Id(WALLET_U2_2)).thenReturn(Optional.empty());

        Executable operation = () -> transferService.transfer(new TransferRequest(WALLET_U1_2, WALLET_U2_2, "100"));
        TransferException exception = assertThrows(TransferException.class, operation);

        assertThat(exception.getMessage()).isEqualTo("User with wallet '" + WALLET_U2_2 + "' was not found.");
    }

    @Test
    @DisplayName("Retries few times if failed with optimistic lock")
    void testTransferDestinationUserNotFound() {
        when(userRepository.findByWallets_Id(USER_ID_1)).thenReturn(Optional.of(user1));
        when(userRepository.findByWallets_Id(USER_ID_2)).thenReturn(Optional.of(user2));
        when(userRepository.saveAll(any())).thenThrow(OptimisticLockingFailureException.class);

        Executable operation = () -> transferService.transfer(new TransferRequest(WALLET_U1_2, WALLET_U2_2, "100"));
        assertThrows(IllegalStateException.class, operation);
        verify(userRepository, times(MAX_RETRIES + 1)).saveAll(any());
    }
}