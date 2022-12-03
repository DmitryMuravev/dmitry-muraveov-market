package com.dmitry.muravev.market.service;

import com.dmitry.muravev.market.dto.request.UpdateClientDiscountsRequest;
import com.dmitry.muravev.market.dto.response.GenericMessageResponse;
import com.dmitry.muravev.market.entity.ClientEntity;

import java.util.UUID;

public interface DiscountService {

    int getClientDiscount(ClientEntity client, int goodsCount);

    int getResultDiscount (int clientDiscount, int goodsDiscount);

    GenericMessageResponse updateClientDiscounts(UUID clientId, UpdateClientDiscountsRequest request);

    int generateGoodsDiscount();

}
