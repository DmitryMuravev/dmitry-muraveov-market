package com.dmitry.muravev.market;

import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.entity.GoodsEntity;
import com.dmitry.muravev.market.entity.RatingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestDataProvider {
    public static final UUID TEST_CLIENT_UUID = UUID.randomUUID();
    public static final UUID TEST_GOODS_UUID = UUID.randomUUID();

    public GoodsEntity generateGoods(int discount, long price) {
        return GoodsEntity.builder()
                .id(UUID.randomUUID())
                .discount(discount)
                .name(UUID.randomUUID().toString())
                .description(UUID.randomUUID().toString())
                .price(price)
                .build();
    }

    public List<GoodsEntity> generateGoodsList(List<Long> prices) {
        List<GoodsEntity> goodsList = new ArrayList<>();

        for (Long price : prices) {
            goodsList.add(generateGoods(0, price));
        }

        return goodsList;
    }

    public RatingEntity generateRating(int value) {
        return RatingEntity.builder()
                .id(UUID.randomUUID())
                .value(value)
                .client(new ClientEntity())
                .goods(new GoodsEntity())
                .build();
    }
    public List<RatingEntity> generateRatingList(List<Integer> rateValues) {
        List<RatingEntity> ratingList = new ArrayList<>();

        for (Integer rateValue : rateValues) {
            ratingList.add(generateRating(rateValue));
        }

        return ratingList;
    }

}
