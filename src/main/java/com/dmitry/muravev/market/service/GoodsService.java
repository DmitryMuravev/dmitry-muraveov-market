package com.dmitry.muravev.market.service;

import com.dmitry.muravev.market.dto.request.RateGoodsRequest;
import com.dmitry.muravev.market.dto.response.GenericMessageResponse;
import com.dmitry.muravev.market.dto.response.GoodsResponse;
import com.dmitry.muravev.market.dto.response.GoodsDetailsResponse;
import com.dmitry.muravev.market.entity.GoodsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GoodsService {

    GenericMessageResponse rateGoods(UUID goodsId, RateGoodsRequest request);

    GoodsResponse getGoods(Pageable pageable);

    GoodsDetailsResponse getGoodsDetails(UUID goodsId, UUID clientId);

    GoodsEntity getById(UUID goodsId);

    Page<GoodsEntity> getAll(Pageable pageable);

    List<GoodsEntity> getByIds(Set<UUID> goodsIds);

    void updateGoodsDiscount();
}
