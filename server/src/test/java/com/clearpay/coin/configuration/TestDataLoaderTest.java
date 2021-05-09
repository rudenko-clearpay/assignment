package com.clearpay.coin.configuration;

import com.clearpay.coin.model.User;
import com.clearpay.coin.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * For better coverage report
 */
class TestDataLoaderTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private TestDataLoader dataLoader;

    private final ArgumentCaptor<List<User>> captor = ArgumentCaptor.forClass(List.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDataProvisioning() {
        dataLoader.onApplicationEvent(mock(ApplicationReadyEvent.class));
        verify(repository).saveAll(captor.capture());
        assertThat(captor.getValue().size()).isEqualTo(21);
    }
}