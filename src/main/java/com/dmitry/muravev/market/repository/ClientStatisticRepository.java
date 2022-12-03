package com.dmitry.muravev.market.repository;

import com.dmitry.muravev.market.entity.ClientStatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientStatisticRepository extends JpaRepository<ClientStatisticEntity, UUID> {
}
