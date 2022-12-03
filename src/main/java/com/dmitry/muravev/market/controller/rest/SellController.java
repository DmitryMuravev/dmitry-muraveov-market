package com.dmitry.muravev.market.controller.rest;

import com.dmitry.muravev.market.dto.request.PurchaseRequest;
import com.dmitry.muravev.market.dto.request.TotalCostRequest;
import com.dmitry.muravev.market.dto.response.PurchaseResponse;
import com.dmitry.muravev.market.dto.response.TotalCostResponse;
import com.dmitry.muravev.market.service.SellService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SellController {
    private final SellService sellService;

    @PostMapping("/basket/cost")
    public TotalCostResponse getTotalCost(@RequestBody @Valid TotalCostRequest request) {
        return sellService.getTotalCost(request);
    }

    @PostMapping("/basket/purchase")
    public PurchaseResponse purchase (@RequestBody @Valid PurchaseRequest request) {
        return sellService.purchase(request);
    }
}
