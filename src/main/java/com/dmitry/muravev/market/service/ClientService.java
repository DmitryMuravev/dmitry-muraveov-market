package com.dmitry.muravev.market.service;

import com.dmitry.muravev.market.dto.response.ClientsResponse;
import com.dmitry.muravev.market.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClientService {

    void save(ClientEntity clientEntity);

    ClientsResponse getClients(Pageable pageable);

    ClientEntity getById(UUID clientId);

    Page<ClientEntity> getAll(Pageable pageable);
}
