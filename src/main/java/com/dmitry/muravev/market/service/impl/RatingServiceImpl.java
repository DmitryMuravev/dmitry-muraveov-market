package com.dmitry.muravev.market.service.impl;

import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.entity.GoodsEntity;
import com.dmitry.muravev.market.entity.RatingEntity;
import com.dmitry.muravev.market.repository.RatingRepository;
import com.dmitry.muravev.market.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public boolean isGoodsRated(GoodsEntity goods, ClientEntity client) {
        return ratingRepository.existsByGoodsAndClient(goods, client);
    }

    @Override
    public void rateGoods(GoodsEntity goods, ClientEntity client, Integer rating) {

        RatingEntity ratingEntity = RatingEntity.builder()
                .goods(goods)
                .client(client)
                .value(rating != null ? rating : 0)
                .build();

        ratingRepository.save(ratingEntity);
    }

    public double calculateAverageRating(List<RatingEntity> ratings) {
        return ratings.stream()
                .mapToDouble(RatingEntity::getValue).average().orElse(0.0);
    }

    public Map<Integer, Long> mapRatings(List<RatingEntity> ratings) {
        return ratings.stream()
                .collect(Collectors.groupingBy(RatingEntity::getValue, Collectors.counting()));
    }

    public Integer getClientRating(List<RatingEntity> ratings, ClientEntity client) {
        return ratings.stream()
                .filter(re -> re.getClient().equals(client))
                .findFirst().map(RatingEntity::getValue)
                .orElse(null);
    }

    @Override
    public List<RatingEntity> getByGoods(GoodsEntity goods) {
        return ratingRepository.findByGoods(goods);
    }
}
