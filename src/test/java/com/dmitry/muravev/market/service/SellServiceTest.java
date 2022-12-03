package com.dmitry.muravev.market.service;

import com.dmitry.muravev.market.TestDataProvider;
import com.dmitry.muravev.market.dto.request.PurchaseRequest;
import com.dmitry.muravev.market.dto.request.TotalCostRequest;
import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.entity.GoodsEntity;
import com.dmitry.muravev.market.repository.SellPositionRepository;
import com.dmitry.muravev.market.repository.SellRepository;
import com.dmitry.muravev.market.service.impl.SellServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SellServiceTest {

    private TestDataProvider testDataProvider;
    private GoodsService goodsService;
    private ClientService clientService;
    private DiscountService discountService;
    private SellRepository sellRepository;
    private SellPositionRepository sellPositionRepository;

    @BeforeEach
    public void init() {
        testDataProvider = new TestDataProvider();
        goodsService = Mockito.mock(GoodsService.class);
        clientService = Mockito.mock(ClientService.class);
        discountService = Mockito.mock(DiscountService.class);
        sellRepository = Mockito.mock(SellRepository.class);
        sellPositionRepository = Mockito.mock(SellPositionRepository.class);
    }

    @Test
    public void whenGetTotalCostThenReturnCorrectValue() {
        long expectedTotalCost = 2160;

        SellService sellService = new SellServiceImpl(goodsService, clientService,
                discountService, sellRepository, sellPositionRepository);

        GoodsEntity firstGoods = testDataProvider.generateGoods(0, 100);
        GoodsEntity secondGoods = testDataProvider.generateGoods(0, 300);
        ClientEntity clientEntity = new ClientEntity();

        Map<UUID, Integer> requestedGoods = new HashMap<>();
        requestedGoods.put(firstGoods.getId(), 3);
        requestedGoods.put(secondGoods.getId(), 7);

        Mockito.when(clientService.getById(Mockito.any())).thenReturn(clientEntity);
        Mockito.when(discountService.getClientDiscount(clientEntity, 10)).thenReturn(5);
        Mockito.when(discountService.getResultDiscount(5, 0)).thenReturn(10);
        Mockito.when(goodsService.getByIds(requestedGoods.keySet()))
                .thenReturn(List.of(firstGoods, secondGoods));

        Assertions.assertEquals(expectedTotalCost,
                sellService.getTotalCost(new TotalCostRequest(TestDataProvider.TEST_CLIENT_UUID,
                        requestedGoods)).getTotalCost());
    }

    @Test
    public void whenPurchaseAndProvidedIncorrectTotalCostThenThrowException() {
        SellService sellService = new SellServiceImpl(goodsService, clientService,
                discountService, sellRepository, sellPositionRepository);

        GoodsEntity firstGoods = testDataProvider.generateGoods(0, 100);
        GoodsEntity secondGoods = testDataProvider.generateGoods(0, 300);
        ClientEntity clientEntity = new ClientEntity();

        Map<UUID, Integer> requestedGoods = new HashMap<>();
        requestedGoods.put(firstGoods.getId(), 3);
        requestedGoods.put(secondGoods.getId(), 7);

        Mockito.when(clientService.getById(Mockito.any())).thenReturn(clientEntity);
        Mockito.when(discountService.getClientDiscount(clientEntity, 10)).thenReturn(5);
        Mockito.when(discountService.getResultDiscount(5, 0)).thenReturn(10);
        Mockito.when(goodsService.getByIds(requestedGoods.keySet()))
                .thenReturn(List.of(firstGoods, secondGoods));

        Assertions.assertThrows(ResponseStatusException.class,
                () -> sellService.purchase(new PurchaseRequest(TestDataProvider.TEST_CLIENT_UUID,
                        requestedGoods, 200L)));
    }
}
