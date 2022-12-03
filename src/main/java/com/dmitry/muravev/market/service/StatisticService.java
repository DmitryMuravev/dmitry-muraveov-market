package com.dmitry.muravev.market.service;

import com.dmitry.muravev.market.dto.request.StatisticType;
import com.dmitry.muravev.market.dto.response.StatisticResponse;

import java.util.UUID;

public interface StatisticService {
    void updateStatistics();

    StatisticResponse getStatistic(UUID id, StatisticType statisticType);

}
