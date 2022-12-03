package com.dmitry.muravev.market.service;

import com.dmitry.muravev.market.TestDataProvider;
import com.dmitry.muravev.market.repository.ClientRepository;
import com.dmitry.muravev.market.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class ClientServiceTest {

    private ClientRepository clientRepository;

    @BeforeEach
    public void init() {
        clientRepository = Mockito.mock(ClientRepository.class);
    }

    @Test
    public void whenGetByIdAndClientNotExistsThenThrowException() {
        ClientService clientService = new ClientServiceImpl(clientRepository);

        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,
                () -> clientService.getById(TestDataProvider.TEST_CLIENT_UUID));
    }
}
