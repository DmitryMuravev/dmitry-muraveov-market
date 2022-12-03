package com.dmitry.muravev.market.service;

import com.dmitry.muravev.market.TestDataProvider;
import com.dmitry.muravev.market.config.DiscountConfig;
import com.dmitry.muravev.market.dto.request.UpdateClientDiscountsRequest;
import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.service.impl.DiscountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

public class DiscountServiceTest {

    private ClientService clientService;
    private DiscountConfig discountConfig;

    @BeforeEach
    public void init() {
        clientService = Mockito.mock(ClientService.class);
        discountConfig = Mockito.mock(DiscountConfig.class);
    }

    @Test
    public void whenGetClientPurchaseDiscountAndGoodsCountLessThanThresholdThanReturnFirstDiscount() {
        DiscountService discountService =
                new DiscountServiceImpl(discountConfig, clientService);

        Mockito.when(discountConfig.getSecondDiscountThreshold()).thenReturn(5);

        int firstDiscount = 10;
        int secondDiscount = 12;
        int goodsCount = 1;

        ClientEntity clientEntity = ClientEntity.builder()
                .firstDiscount(firstDiscount)
                .secondDiscount(secondDiscount)
                .build();

        Assertions.assertEquals(firstDiscount,
                discountService.getClientDiscount(clientEntity, goodsCount));

    }

    @Test
    public void whenGetClientPurchaseDiscountAndGoodsCountGreaterThanThresholdThanReturnSecondDiscount() {
        DiscountService discountService =
                new DiscountServiceImpl(discountConfig, clientService);

        Mockito.when(discountConfig.getSecondDiscountThreshold()).thenReturn(5);

        int firstDiscount = 10;
        int secondDiscount = 12;
        int goodsCount = 6;

        ClientEntity clientEntity = ClientEntity.builder()
                .firstDiscount(firstDiscount)
                .secondDiscount(secondDiscount)
                .build();

        Assertions.assertEquals(secondDiscount,
                discountService.getClientDiscount(clientEntity, goodsCount));

    }

    @Test
    public void whenUpdateClientDiscountsAndNewDiscountLessThanMinValueThenThrowException() {
        DiscountService discountService =
                new DiscountServiceImpl(discountConfig, clientService);

        int firstDiscount = 1;
        int secondDiscount = 2;

        UpdateClientDiscountsRequest request =
                new UpdateClientDiscountsRequest(firstDiscount, secondDiscount);

        Mockito.when(discountConfig.getMinClientDiscount()).thenReturn(10);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> discountService.updateClientDiscounts(TestDataProvider.TEST_CLIENT_UUID, request));
    }

    @Test
    public void whenUpdateClientDiscountsAndNewDiscountGreaterThanMaxValueThenThrowException() {
        DiscountService discountService =
                new DiscountServiceImpl(discountConfig, clientService);

        int firstDiscount = 11;
        int secondDiscount = 12;

        UpdateClientDiscountsRequest request =
                new UpdateClientDiscountsRequest(firstDiscount, secondDiscount);

        Mockito.when(discountConfig.getMaxClientDiscount()).thenReturn(10);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> discountService.updateClientDiscounts(TestDataProvider.TEST_CLIENT_UUID, request));
    }

    @ParameterizedTest
    @CsvSource({"1,11", "2,12", "12,18", "24,18"})
    public void whenGetResultDiscountThenReturnCorrectValue(int goodsDiscount, int expectedTotalDiscount) {
        DiscountService discountService =
                new DiscountServiceImpl(discountConfig, clientService);

        int clientDiscount = 10;

        Mockito.when(discountConfig.getMaxTotalDiscount()).thenReturn(18);

        Assertions.assertEquals(expectedTotalDiscount,
                discountService.getResultDiscount(clientDiscount, goodsDiscount));
    }
}
