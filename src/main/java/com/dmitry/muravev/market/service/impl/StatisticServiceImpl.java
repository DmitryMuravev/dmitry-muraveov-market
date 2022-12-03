package com.dmitry.muravev.market.service.impl;

import com.dmitry.muravev.market.dto.request.StatisticType;
import com.dmitry.muravev.market.dto.response.StatisticResponse;
import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.entity.ClientStatisticEntity;
import com.dmitry.muravev.market.entity.GoodsEntity;
import com.dmitry.muravev.market.entity.GoodsStatisticEntity;
import com.dmitry.muravev.market.entity.SellEntity;
import com.dmitry.muravev.market.entity.SellPositionEntity;
import com.dmitry.muravev.market.entity.StatisticEntity;
import com.dmitry.muravev.market.repository.ClientStatisticRepository;
import com.dmitry.muravev.market.repository.GoodsStatisticRepository;
import com.dmitry.muravev.market.repository.SellPositionRepository;
import com.dmitry.muravev.market.repository.SellRepository;
import com.dmitry.muravev.market.service.ClientService;
import com.dmitry.muravev.market.service.GoodsService;
import com.dmitry.muravev.market.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private static final int CLIENT_PAGE_SIZE = 100;
    private static final int GOODS_PAGE_SIZE = 100;
    private static final int SELL_PAGE_SIZE = 10;

    private final SellRepository sellRepository;
    private final ClientService clientService;
    private final GoodsService goodsService;
    private final ClientStatisticRepository clientStatisticRepository;
    private final GoodsStatisticRepository goodsStatisticRepository;
    private final SellPositionRepository sellPositionRepository;

    @Override
    public void updateStatistics() {
        updateGoodsStatistics();
        updateClientStatistics();
    }

    @Override
    public StatisticResponse getStatistic(UUID id, StatisticType statisticType) {
        StatisticEntity statistic;

        switch (statisticType) {
            case GOODS:
                statistic = getGoodsStatistic(id);
                break;
            case CLIENT:
                statistic = getClientStatistic(id);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Provided unsupported statistic type");
        }

        return mapToStatisticResponse(statistic);

    }

    private GoodsStatisticEntity getGoodsStatistic(UUID id) {
        return goodsStatisticRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Statistic for goods with id " + id + " not found"));
    }

    private ClientStatisticEntity getClientStatistic(UUID id) {
        return clientStatisticRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Statistic for client with id " + id + " not found"));
    }

    private StatisticResponse mapToStatisticResponse(StatisticEntity statistic) {
        return StatisticResponse.builder()
                .checkCount(statistic.getCheckCount())
                .totalCost(statistic.getTotalCost())
                .totalDiscount(statistic.getTotalDiscount())
                .build();
    }

    private void updateClientStatistics() {
        Page<ClientEntity> clients;

        int pageId = 0;

        do {
            clients =  clientService.getAll(PageRequest.of(pageId, CLIENT_PAGE_SIZE));

            List<ClientStatisticEntity> clientsStatistic = clients.stream()
                    .map(this::createClientStatistic).collect(Collectors.toList());

            clientStatisticRepository.saveAll(clientsStatistic);
            pageId++;
        } while (!clients.isEmpty());
    }

    private ClientStatisticEntity createClientStatistic(ClientEntity client) {
        Page<SellEntity> sells;
        ClientStatisticEntity currentClientStatistic =
                new ClientStatisticEntity(client.getId(), 0, 0L, 0L);

        int pageId = 0;
        int checkCount = 0;

        do {
            sells = sellRepository.findByClient(client, PageRequest.of(pageId, SELL_PAGE_SIZE));
            checkCount += sells.getNumberOfElements();
            sells.stream()
                    .flatMap(s -> s.getPositions().stream())
                    .forEach(p -> addSellPositionDataToStatisticEntity(currentClientStatistic, p));

            pageId++;
        } while (!sells.isEmpty());

        currentClientStatistic.setCheckCount(checkCount);

        return currentClientStatistic;
    }

    private void updateGoodsStatistics() {
        Page<GoodsEntity> goods;

        int pageId = 0;

        do {
            goods = goodsService.getAll(PageRequest.of(pageId, GOODS_PAGE_SIZE));

            List<GoodsStatisticEntity> clientsStatistic = goods.stream()
                    .map(this::createGoodsStatistic).collect(Collectors.toList());

            goodsStatisticRepository.saveAll(clientsStatistic);
            pageId++;
        } while (!goods.isEmpty());
    }

    private GoodsStatisticEntity createGoodsStatistic(GoodsEntity goods) {
        Page<SellPositionEntity> positions;
        GoodsStatisticEntity currentGoodsStatistic =
                new GoodsStatisticEntity(goods.getId(), 0, 0L, 0L);

        Set<SellEntity> sellsSet = new HashSet<>();

        int pageId = 0;

        do {
            positions = sellPositionRepository.findByGoods(goods, PageRequest.of(pageId, SELL_PAGE_SIZE));
            positions.forEach(p -> sellsSet.add(p.getSell()));
            positions.forEach(p -> addSellPositionDataToStatisticEntity(currentGoodsStatistic, p));

            pageId++;
        } while (!positions.isEmpty());

        currentGoodsStatistic.setCheckCount(sellsSet.size());
        return currentGoodsStatistic;
    }



    private void addSellPositionDataToStatisticEntity(StatisticEntity statistic,
                                                      SellPositionEntity position) {
        statistic.setTotalCost(statistic.getTotalCost() + position.getInitialCost());
        statistic.setTotalDiscount(statistic.getTotalDiscount()
                + position.getInitialCost() - position.getResultCost());
    }

}
