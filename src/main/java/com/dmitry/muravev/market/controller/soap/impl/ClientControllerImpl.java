package com.dmitry.muravev.market.controller.soap.impl;

import com.dmitry.muravev.market.controller.soap.ClientController;
import com.dmitry.muravev.market.dto.request.UpdateClientDiscountsRequest;
import com.dmitry.muravev.market.dto.response.GenericMessageResponse;
import com.dmitry.muravev.market.dto.response.ClientsResponse;
import com.dmitry.muravev.market.service.ClientService;
import com.dmitry.muravev.market.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.UUID;

@Component
@WebService(
        serviceName = "ClientService",
        portName = "ClientPort",
        targetNamespace = "http://market.muravev.dmitry.com/",
        endpointInterface = "com.dmitry.muravev.market.controller.soap.ClientController")
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {

    private final ClientService clientService;
    private final DiscountService discountService;

    @Override
    public ClientsResponse getClients() {
        return clientService.getClients(Pageable.unpaged());
    }

    @Override
    public GenericMessageResponse updateClientDiscounts(UUID clientId, UpdateClientDiscountsRequest request) {
        return discountService.updateClientDiscounts(clientId, request);
    }
}
