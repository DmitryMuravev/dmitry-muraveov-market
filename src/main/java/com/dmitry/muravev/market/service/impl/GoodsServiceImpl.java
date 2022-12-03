package com.dmitry.muravev.market.service.impl;

import com.dmitry.muravev.market.dto.request.RateGoodsRequest;
import com.dmitry.muravev.market.dto.response.GenericMessageResponse;
import com.dmitry.muravev.market.dto.response.GoodsResponse;
import com.dmitry.muravev.market.dto.response.GoodsDetailsResponse;
import com.dmitry.muravev.market.dto.response.ShortGoodsData;
import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.entity.GoodsEntity;
import com.dmitry.muravev.market.entity.RatingEntity;
import com.dmitry.muravev.market.repository.GoodsRepository;
import com.dmitry.muravev.market.service.ClientService;
import com.dmitry.muravev.market.service.DiscountService;
import com.dmitry.muravev.market.service.GoodsService;
import com.dmitry.muravev.market.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;
    private final RatingService ratingService;
    private final ClientService clientService;
    private final DiscountService discountService;

    @Override
    public GenericMessageResponse rateGoods(UUID goodsId, RateGoodsRequest request) {

        GoodsEntity goods = getById(goodsId);
        ClientEntity client = clientService.getById(request.getClientId());

        if (ratingService.isGoodsRated(goods, client)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Goods already rated by client " + request.getClientId());
        }

        ratingService.rateGoods(goods, client, request.getRatingValue());

        return new GenericMessageResponse("Goods successfully rated");
    }

    public GoodsResponse getGoods(Pageable pageable) {
        Page<GoodsEntity> goods = goodsRepository.findAll(pageable);

        List<ShortGoodsData> goodsData = goods.stream()
                .map(ge -> ShortGoodsData.builder()
                        .goodsId(ge.getId())
                        .name(ge.getName())
                        .price(ge.getPrice())
                        .build())
                .collect(Collectors.toList());

        return new GoodsResponse(goodsData);
    }

    public GoodsDetailsResponse getGoodsDetails(UUID goodsId, UUID clientId) {
        GoodsEntity goods = getById(goodsId);
        ClientEntity client = clientService.getById(clientId);

        List<RatingEntity> ratings = ratingService.getByGoods(goods);

        return GoodsDetailsResponse.builder()
                .description(goods.getDescription())
                .averageRating(ratingService.calculateAverageRating(ratings))
                .ratings(ratingService.mapRatings(ratings))
                .clientRating(ratingService.getClientRating(ratings, client))
                .build();
    }

    public GoodsEntity getById(UUID goodsId) {
        return goodsRepository.findById(goodsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Goods with id " + goodsId + " not exists"));
    }

    @Override
    public Page<GoodsEntity> getAll(Pageable pageable) {
        return goodsRepository.findAll(pageable);
    }

    public List<GoodsEntity> getByIds(Set<UUID> goodsIds) {
        List<GoodsEntity> goods = goodsRepository.findByIdIn(goodsIds);

        if (goods.size() != goodsIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Provided incorrect list of goods");
        }
        return goods;
    }

    @Transactional
    public void updateGoodsDiscount() {
        Optional<GoodsEntity> optionalPreviousDiscountedGoods = goodsRepository.findByDiscountGreaterThan(0);

        if (optionalPreviousDiscountedGoods.isPresent()) {
            GoodsEntity previousDiscountedGoods =
                    optionalPreviousDiscountedGoods.get();
            previousDiscountedGoods.setDiscount(0);
            goodsRepository.save(previousDiscountedGoods);
        }

        Page<GoodsEntity> goods = goodsRepository.findAll(Pageable.unpaged());

        Optional<GoodsEntity> optionalNewDiscountedGoods = goods.get()

                .findAny();

        if (optionalNewDiscountedGoods.isPresent()) {
            GoodsEntity newDiscountedGoods = optionalNewDiscountedGoods.get();
            newDiscountedGoods.setDiscount(discountService.generateGoodsDiscount());
            goodsRepository.save(newDiscountedGoods);
        }
    }
}
