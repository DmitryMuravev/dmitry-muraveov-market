package com.dmitry.muravev.market.service.impl;

import com.dmitry.muravev.market.dto.request.PurchaseRequest;
import com.dmitry.muravev.market.dto.request.TotalCostRequest;
import com.dmitry.muravev.market.dto.response.PurchaseResponse;
import com.dmitry.muravev.market.dto.response.TotalCostResponse;
import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.entity.GoodsEntity;
import com.dmitry.muravev.market.entity.SellEntity;
import com.dmitry.muravev.market.entity.SellPositionEntity;
import com.dmitry.muravev.market.repository.SellPositionRepository;
import com.dmitry.muravev.market.repository.SellRepository;
import com.dmitry.muravev.market.service.ClientService;
import com.dmitry.muravev.market.service.DiscountService;
import com.dmitry.muravev.market.service.GoodsService;
import com.dmitry.muravev.market.service.SellService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellServiceImpl implements SellService {

    private static final String CHECK_NUMBER_FORMAT = "%05d";

    private final GoodsService goodsService;
    private final ClientService clientService;
    private final DiscountService discountService;
    private final SellRepository sellRepository;
    private final SellPositionRepository sellPositionRepository;

    @Override
    public TotalCostResponse getTotalCost(TotalCostRequest request) {
        ClientEntity client = clientService.getById(request.getClientId());
        Map<GoodsEntity, Integer> requestedGoodsEntities = createGoodsMap(request.getGoods());

        return new TotalCostResponse(calculateTotalCost(requestedGoodsEntities, client));
    }

    @Transactional
    public PurchaseResponse purchase(PurchaseRequest request) {
        ClientEntity client = clientService.getById(request.getClientId());
        Map<GoodsEntity, Integer> requestedGoodsEntities = createGoodsMap(request.getGoods());

        long actualTotalCost = calculateTotalCost(requestedGoodsEntities, client);

        if (actualTotalCost != request.getTotalCost()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Provided incorrect goods total cost");
        }

        int clientDiscount = discountService
                .getClientDiscount(client, getCount(request.getGoods()));

        List<SellPositionEntity> positions = requestedGoodsEntities.keySet().stream()
                .map(ge -> SellPositionEntity.builder()
                        .goods(ge)
                        .count(requestedGoodsEntities.get(ge))
                        .initialCost(ge.getPrice() *requestedGoodsEntities.get(ge))
                        .resultCost(calculateResultCost(clientDiscount, ge,
                                requestedGoodsEntities.get(ge)))
                        .resultDiscount(discountService.getResultDiscount(clientDiscount, ge.getDiscount()))
                        .build())
                .collect(Collectors.toList());

        sellPositionRepository.saveAll(positions);

        SellEntity sell = SellEntity.builder()
                .client(client)
                .positions(positions)
                .sellDate(LocalDate.now())
                .build();

        sell = sellRepository.save(sell);

        return new PurchaseResponse(String.format(CHECK_NUMBER_FORMAT, sell.getCheckNumber()));
    }

    private long calculateTotalCost(Map<GoodsEntity, Integer> goodsMap, ClientEntity client) {

        int clientDiscount = discountService
                .getClientDiscount(client, getCount(goodsMap));

        return goodsMap.keySet().stream()
                .mapToLong(ge -> calculateResultCost(clientDiscount, ge, goodsMap.get(ge)))
                .sum();
    }

    private long calculateResultCost(int clientDiscount, GoodsEntity goods, int count) {

        int resultDiscount = discountService.getResultDiscount(clientDiscount, goods.getDiscount());

        return (goods.getPrice() * (100 - resultDiscount)) / 100 * count;

    }

    private int getCount(Map<?, Integer> requestedGoods) {
        return requestedGoods.keySet().stream()
                .mapToInt(requestedGoods::get)
                .sum();
    }

    private Map<GoodsEntity, Integer> createGoodsMap(Map<UUID, Integer> requestedGoods) {
        List<GoodsEntity> goods = goodsService.getByIds(requestedGoods.keySet());

        return goods.stream()
                .filter(g -> requestedGoods.get(g.getId()) != null)
                .collect(Collectors.toMap(Function.identity(), ge -> requestedGoods.get(ge.getId())));
    }

}
