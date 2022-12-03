package com.dmitry.muravev.market.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailsResponse {
    private String description;
    private Double averageRating;
    private Map<Integer, Long> ratings;
    private Integer clientRating;
}
