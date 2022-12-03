package com.dmitry.muravev.market.repository;

import com.dmitry.muravev.market.entity.GoodsEntity;
import com.dmitry.muravev.market.entity.SellPositionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface SellPositionRepository extends PagingAndSortingRepository<SellPositionEntity, UUID> {
    Page<SellPositionEntity> findByGoods(GoodsEntity goodsEntity, Pageable pageable);
}
