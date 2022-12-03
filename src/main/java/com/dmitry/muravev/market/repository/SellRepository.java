package com.dmitry.muravev.market.repository;

import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.entity.SellEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface SellRepository extends PagingAndSortingRepository<SellEntity, UUID> {

    @EntityGraph(value = "sell-entity-graph")
    Page<SellEntity> findByClient(ClientEntity client, Pageable pageable);

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query(value = "ALTER SEQUENCE check_num_seq RESTART WITH 100;", nativeQuery = true)
    void resetSequence();
}
