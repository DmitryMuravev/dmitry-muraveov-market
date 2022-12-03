package com.dmitry.muravev.market.controller.rest;

import com.dmitry.muravev.market.dto.request.StatisticType;
import com.dmitry.muravev.market.dto.response.StatisticResponse;
import com.dmitry.muravev.market.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/statistics")
    public StatisticResponse getStatistic(@RequestParam UUID id,
                                          @RequestParam StatisticType statisticType) {
        return statisticService.getStatistic(id, statisticType);
    }

}
