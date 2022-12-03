package com.dmitry.muravev.market.controller.soap.impl;

import com.dmitry.muravev.market.controller.soap.StatisticController;
import com.dmitry.muravev.market.dto.request.StatisticType;
import com.dmitry.muravev.market.dto.response.StatisticResponse;
import com.dmitry.muravev.market.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.UUID;

@Component
@WebService(
        serviceName = "StatisticService",
        portName = "StatisticPort",
        targetNamespace = "http://market.muravev.dmitry.com/",
        endpointInterface = "com.dmitry.muravev.market.controller.soap.StatisticController")
@RequiredArgsConstructor
public class StatisticControllerImpl implements StatisticController {
    private final StatisticService statisticService;

    @Override
    public StatisticResponse getStatistic(UUID id, StatisticType statisticType) {
        return statisticService.getStatistic(id, statisticType);
    }
}
