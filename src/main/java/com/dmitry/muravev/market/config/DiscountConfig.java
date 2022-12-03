package com.dmitry.muravev.market.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "discount")
public class DiscountConfig {

    private int minTotalDiscount;
    private int maxTotalDiscount;
    private int minGoodsDiscount;
    private int maxGoodsDiscount;
    private int minClientDiscount;
    private int MaxClientDiscount;
    private int secondDiscountThreshold;


}
