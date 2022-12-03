package com.dmitry.muravev.market.controller.rest;

import com.dmitry.muravev.market.dto.request.UpdateClientDiscountsRequest;
import com.dmitry.muravev.market.dto.response.ClientsResponse;
import com.dmitry.muravev.market.dto.response.GenericMessageResponse;
import com.dmitry.muravev.market.service.ClientService;
import com.dmitry.muravev.market.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final DiscountService discountService;

    @GetMapping("/clients")
    public ClientsResponse getClients(@ApiIgnore Pageable pageable) {
        return clientService.getClients(pageable);
    }

    @PostMapping("/clients/{clientId}/discounts")
    public GenericMessageResponse updateClientDiscounts(@PathVariable("clientId") UUID clientId,
                                                        @RequestBody UpdateClientDiscountsRequest request) {
        return discountService.updateClientDiscounts(clientId, request);
    }
}
