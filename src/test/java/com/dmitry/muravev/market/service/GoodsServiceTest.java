package com.dmitry.muravev.market.service;

import com.dmitry.muravev.market.TestDataProvider;
import com.dmitry.muravev.market.dto.request.RateGoodsRequest;
import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.entity.GoodsEntity;
import com.dmitry.muravev.market.repository.GoodsRepository;
import com.dmitry.muravev.market.service.impl.GoodsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class GoodsServiceTest {
    private TestDataProvider testDataProvider;

    private GoodsRepository goodsRepository;
    private RatingService ratingService;
    private DiscountService discountService;
    private ClientService clientService;


    @BeforeEach
    public void init() {
        testDataProvider = new TestDataProvider();

        goodsRepository = Mockito.mock(GoodsRepository.class);
        ratingService = Mockito.mock(RatingService.class);
        discountService = Mockito.mock(DiscountService.class);
        clientService = Mockito.mock(ClientService.class);
    }

    @Test
    public void whenRateGoodsAndGoodsNotExistsThenThrowException() {
        RateGoodsRequest request =
                new RateGoodsRequest(TestDataProvider.TEST_CLIENT_UUID, 1);

        GoodsService goodsService = new GoodsServiceImpl(goodsRepository,
                ratingService, clientService, discountService);

        Mockito.when(goodsRepository.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,
                () -> goodsService.rateGoods(TestDataProvider.TEST_GOODS_UUID, request));
    }

    @Test
    public void whenRateGoodsAndGoodsAlreadyRatedThenThrowException() {
        RateGoodsRequest request =
                new RateGoodsRequest(TestDataProvider.TEST_CLIENT_UUID, 1);

        GoodsService goodsService = new GoodsServiceImpl(goodsRepository,
                ratingService, clientService, discountService);

        Mockito.when(goodsRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(new GoodsEntity()));

        Mockito.when(clientService.getById(Mockito.any()))
                .thenReturn(new ClientEntity());

        Mockito.when(ratingService.isGoodsRated(Mockito.any(), Mockito.any())).thenReturn(true);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> goodsService.rateGoods(TestDataProvider.TEST_GOODS_UUID, request));
    }

    @Test
    public void whenGetGoodsDetailsAndGoodsNotExistsThenThrowException() {

        GoodsService goodsService = new GoodsServiceImpl(goodsRepository,
                ratingService, clientService, discountService);

        Mockito.when(goodsRepository.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,
                () -> goodsService.getGoodsDetails(TestDataProvider.TEST_GOODS_UUID, TestDataProvider.TEST_CLIENT_UUID));
    }

    @Test
    public void whenUpdateGoodsDiscountThenPreviousDiscountedGoodsDiscountSetToZero() {
        List<GoodsEntity> goodsList =
                testDataProvider.generateGoodsList(List.of(1L, 2L, 3L, 4L, 5L));

        GoodsEntity discountedGoods = testDataProvider.generateGoods(10, 10);

        goodsList.add(discountedGoods);

        Page<GoodsEntity> goodsPage = new PageImpl<>(goodsList);

        GoodsService goodsService = new GoodsServiceImpl(goodsRepository,
                ratingService, clientService, discountService);

        Mockito.when(goodsRepository.findByDiscountGreaterThan(0))
                .thenReturn(Optional.of(discountedGoods));
        Mockito.when(goodsRepository.findAll(Mockito.any(Pageable.class))).thenReturn(goodsPage);

        goodsService.updateGoodsDiscount();

        Assertions.assertEquals(0, (int) discountedGoods.getDiscount());
    }

    @Test
    public void whenUpdateGoodsDiscountThenDiscountedGoodsDiscountSetToNonZero() {
        List<GoodsEntity> goodsList =
                testDataProvider.generateGoodsList(List.of(1L, 2L, 3L, 4L, 5L));

        Page<GoodsEntity> goodsPage = new PageImpl<>(goodsList);

        GoodsService goodsService = new GoodsServiceImpl(goodsRepository,
                ratingService, clientService, discountService);

        Mockito.when(goodsRepository.findByDiscountGreaterThan(0))
                .thenReturn(Optional.empty());
        Mockito.when(goodsRepository.findAll(Mockito.any(Pageable.class))).thenReturn(goodsPage);
        Mockito.when(discountService.generateGoodsDiscount()).thenReturn(12);

        goodsService.updateGoodsDiscount();

        long discountedGoodsCount = goodsPage.stream().filter(ge -> ge.getDiscount() != 0).count();

        Assertions.assertEquals(1, discountedGoodsCount);
    }
}
