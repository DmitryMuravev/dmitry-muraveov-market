package com.dmitry.muravev.market.entity;



public interface StatisticEntity {
    int getCheckCount();
    void setCheckCount(int checkCount);

    Long getTotalCost();
    void setTotalCost(Long totalCost);

    Long getTotalDiscount();
    void setTotalDiscount(Long totalDiscount);
}
