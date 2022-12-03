package com.dmitry.muravev.market.service.impl;

import com.dmitry.muravev.market.repository.SellRepository;
import com.dmitry.muravev.market.service.GoodsService;
import com.dmitry.muravev.market.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulingService {
    private final GoodsService goodsService;
    private final StatisticService statisticService;
    private final SellRepository sellRepository;

    @Scheduled(cron = "${cron.update-goods-discount}")
    public void runGoodsDiscountUpdate(){
        log.debug("Start goods discount update job");
        goodsService.updateGoodsDiscount();
        log.debug("End goods discount update job");

    }

    @Scheduled(cron = "${cron.update-statistic}")
    public void runStatisticUpdate(){
        log.debug("Start statistic update job");
        statisticService.updateStatistics();
        log.debug("End statistic update job");

    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetSellCheckNumberSequence() {
        log.debug("Start reset check number job");
        sellRepository.resetSequence();
        log.debug("End reset check number job");
    }
}
