package com.dmitry.muravev.market.service;

import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.entity.GoodsEntity;
import com.dmitry.muravev.market.entity.RatingEntity;

import java.util.List;
import java.util.Map;

public interface RatingService {

    boolean isGoodsRated(GoodsEntity goods, ClientEntity client);

    void rateGoods(GoodsEntity goods, ClientEntity client, Integer rating);

    double calculateAverageRating(List<RatingEntity> ratings);

    Map<Integer, Long> mapRatings(List<RatingEntity> ratings);

    Integer getClientRating(List<RatingEntity> ratings, ClientEntity client);

    List<RatingEntity> getByGoods(GoodsEntity goods);
}
