package com.dmitry.muravev.market.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalCostRequest {
    @NotNull
    private UUID clientId;
    @NotNull
    private Map<UUID, Integer> goods;
}
