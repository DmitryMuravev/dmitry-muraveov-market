package com.dmitry.muravev.market.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class PurchaseRequest {
    @NotNull
    @Schema(example = "01a6dfee-b56f-4c86-b9a2-f6267b3d90f6")
    private UUID clientId;
    @NotNull
    private Map<UUID, Integer> goods;
    @Schema(example = "1000")
    private long totalCost;
}
