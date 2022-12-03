package com.dmitry.muravev.market.controller.rest;

import com.dmitry.muravev.market.dto.request.RateGoodsRequest;
import com.dmitry.muravev.market.dto.response.GenericMessageResponse;
import com.dmitry.muravev.market.dto.response.GoodsResponse;
import com.dmitry.muravev.market.dto.response.GoodsDetailsResponse;
import com.dmitry.muravev.market.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/goods")
    public GoodsResponse getGoods(@ApiIgnore Pageable pageable) {
        return goodsService.getGoods(pageable);
    }

    @GetMapping("/goods/{goodsId}")
    public GoodsDetailsResponse getGoodsDetails(@PathVariable("goodsId") UUID goodsId,
                                                @RequestParam("clientId") UUID clientId) {
        return goodsService.getGoodsDetails(goodsId, clientId);
    }

    @PostMapping("/goods/{goodsId}/ratings")
    public GenericMessageResponse rateGoods(@PathVariable("goodsId") UUID goodsId,
                                            @RequestBody @Valid RateGoodsRequest request) {
        return goodsService.rateGoods(goodsId, request);
    }
}
