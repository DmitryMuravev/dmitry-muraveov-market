package com.dmitry.muravev.market.controller.soap.impl;

import com.dmitry.muravev.market.controller.soap.SellController;
import com.dmitry.muravev.market.dto.request.PurchaseRequest;
import com.dmitry.muravev.market.dto.request.TotalCostRequest;
import com.dmitry.muravev.market.dto.response.PurchaseResponse;
import com.dmitry.muravev.market.dto.response.TotalCostResponse;
import com.dmitry.muravev.market.service.SellService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@Component
@WebService(
        serviceName = "SellService",
        portName = "SellPort",
        targetNamespace = "http://market.muravev.dmitry.com/",
        endpointInterface = "com.dmitry.muravev.market.controller.soap.SellController")
@RequiredArgsConstructor
public class SellControllerImpl implements SellController {
    private final SellService sellService;

    @Override
    public TotalCostResponse getTotalCost(TotalCostRequest request) {
        return sellService.getTotalCost(request);
    }

    @Override
    public PurchaseResponse purchase(PurchaseRequest request) {
        return sellService.purchase(request);
    }
}
