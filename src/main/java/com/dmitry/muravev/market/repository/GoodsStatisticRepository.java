package com.dmitry.muravev.market.repository;

import com.dmitry.muravev.market.entity.GoodsStatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoodsStatisticRepository extends JpaRepository<GoodsStatisticEntity, UUID> {
}
