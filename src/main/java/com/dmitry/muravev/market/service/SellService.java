package com.dmitry.muravev.market.service;

import com.dmitry.muravev.market.dto.request.PurchaseRequest;
import com.dmitry.muravev.market.dto.request.TotalCostRequest;
import com.dmitry.muravev.market.dto.response.PurchaseResponse;
import com.dmitry.muravev.market.dto.response.TotalCostResponse;

public interface SellService {

    TotalCostResponse getTotalCost(TotalCostRequest request);

    PurchaseResponse purchase(PurchaseRequest request);
}
