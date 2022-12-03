package com.dmitry.muravev.market.repository;

import com.dmitry.muravev.market.entity.GoodsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface GoodsRepository extends PagingAndSortingRepository<GoodsEntity, UUID> {
    List<GoodsEntity> findByIdIn(Set<UUID> ids);

    Optional<GoodsEntity> findByDiscountGreaterThan(int discount);
}
