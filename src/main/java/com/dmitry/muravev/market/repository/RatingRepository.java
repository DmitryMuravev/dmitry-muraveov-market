package com.dmitry.muravev.market.repository;

import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.entity.GoodsEntity;
import com.dmitry.muravev.market.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<RatingEntity, UUID> {

    List<RatingEntity> findByGoods(GoodsEntity goods);

    boolean existsByGoodsAndClient(GoodsEntity goods, ClientEntity client);
}
