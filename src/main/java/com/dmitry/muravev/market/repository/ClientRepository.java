package com.dmitry.muravev.market.repository;

import com.dmitry.muravev.market.entity.ClientEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ClientRepository extends PagingAndSortingRepository<ClientEntity, UUID> {

}
