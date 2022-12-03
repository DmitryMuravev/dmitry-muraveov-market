package com.dmitry.muravev.market.entity.id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellId implements Serializable {

    private Integer checkNumber;

    private LocalDate sellDate;

}
