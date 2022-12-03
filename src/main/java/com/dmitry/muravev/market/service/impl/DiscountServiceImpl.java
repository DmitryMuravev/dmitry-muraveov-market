package com.dmitry.muravev.market.service.impl;

import com.dmitry.muravev.market.config.DiscountConfig;
import com.dmitry.muravev.market.dto.request.UpdateClientDiscountsRequest;
import com.dmitry.muravev.market.dto.response.GenericMessageResponse;
import com.dmitry.muravev.market.entity.ClientEntity;
import com.dmitry.muravev.market.service.ClientService;
import com.dmitry.muravev.market.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountConfig discountConfig;
    private final ClientService clientService;


    @Override
    public int getClientDiscount(ClientEntity client, int goodsCount) {
        if (goodsCount >= discountConfig.getSecondDiscountThreshold()
                && client.getSecondDiscount() > 0) {
            return client.getSecondDiscount();
        } else {
            return client.getFirstDiscount();
        }
    }

    @Override
    public GenericMessageResponse updateClientDiscounts(UUID clientId, UpdateClientDiscountsRequest request) {
        ClientEntity client = clientService.getById(clientId);

        validDiscounts(List.of(request.getFirstDiscount(), request.getSecondDiscount()));

        client.setFirstDiscount(request.getFirstDiscount());
        client.setSecondDiscount(request.getSecondDiscount());

        clientService.save(client);

        return new GenericMessageResponse("Client discounts successfully updated");

    }

    @Override
    public int generateGoodsDiscount() {
        return ThreadLocalRandom.current().nextInt(discountConfig.getMinGoodsDiscount(),
                discountConfig.getMaxGoodsDiscount());
    }

    public int getResultDiscount (int clientDiscount, int goodsDiscount) {
        return Math.min(clientDiscount + goodsDiscount, discountConfig.getMaxTotalDiscount());
    }

    private void validClientDiscount(int discount) {
        if (discount < discountConfig.getMinClientDiscount() || discount > discountConfig.getMaxClientDiscount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect discount. Values must be between " + discountConfig.getMinGoodsDiscount() +
                            " and " + discountConfig.getMaxGoodsDiscount());
        }
    }

    private void validDiscounts(List<Integer> discounts) {
        discounts.forEach(this::validClientDiscount);
    }
}
