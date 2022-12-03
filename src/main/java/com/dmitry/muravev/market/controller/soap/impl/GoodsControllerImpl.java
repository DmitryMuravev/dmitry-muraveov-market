package com.dmitry.muravev.market.controller.soap.impl;

import com.dmitry.muravev.market.controller.soap.GoodsController;
import com.dmitry.muravev.market.dto.request.RateGoodsRequest;
import com.dmitry.muravev.market.dto.response.GenericMessageResponse;
import com.dmitry.muravev.market.dto.response.GoodsResponse;
import com.dmitry.muravev.market.dto.response.GoodsDetailsResponse;
import com.dmitry.muravev.market.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.UUID;

@Component
@WebService(
        serviceName = "GoodsService",
        portName = "GoodsPort",
        targetNamespace = "http://market.muravev.dmitry.com/",
        endpointInterface = "com.dmitry.muravev.market.controller.soap.GoodsController")
@RequiredArgsConstructor
public class GoodsControllerImpl implements GoodsController {

    private final GoodsService goodsService;

    @Override
    public GoodsResponse getGoods() {
        return goodsService.getGoods(Pageable.unpaged());
    }

    @Override
    public GoodsDetailsResponse getGoodsDetails(UUID goodsId, UUID clientId) {
        return goodsService.getGoodsDetails(goodsId, clientId);
    }

    @Override
    public GenericMessageResponse rateGoods(UUID goodsId, RateGoodsRequest request) {
        return goodsService.rateGoods(goodsId, request);
    }
}
