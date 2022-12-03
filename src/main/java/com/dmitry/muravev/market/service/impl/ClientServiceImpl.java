package com.dmitry.muravev.market.service.impl;

import com.dmitry.muravev.market.dto.response.ClientData;
import com.dmitry.muravev.market.dto.response.ClientsResponse;
import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.repository.ClientRepository;
import com.dmitry.muravev.market.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public void save(ClientEntity clientEntity) {
        clientRepository.save(clientEntity);
    }

    @Override
    public ClientsResponse getClients(Pageable pageable) {
        Page<ClientEntity> clients = clientRepository.findAll(pageable);
        List<ClientData> clientsData = clients.stream().map(ce -> ClientData.builder()
                .clientId(ce.getId())
                .name(ce.getName())
                .firstDiscount(ce.getFirstDiscount())
                .secondDiscount(ce.getSecondDiscount())
                .build()).collect(Collectors.toList());

        return new ClientsResponse(clientsData);
    }

    @Override
    public ClientEntity getById(UUID clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Client with id " + clientId + " not exists"));
    }

    @Override
    public Page<ClientEntity> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

}
