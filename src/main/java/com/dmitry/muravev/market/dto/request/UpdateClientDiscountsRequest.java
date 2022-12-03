package com.dmitry.muravev.market.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientDiscountsRequest {
    private int firstDiscount;
    private int secondDiscount;
}
