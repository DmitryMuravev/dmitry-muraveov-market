package com.dmitry.muravev.market.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RateGoodsRequest {
    @NotNull
    @Schema(example = "01a6dfee-b56f-4c86-b9a2-f6267b3d90f6")
    private UUID clientId;
    @Schema(example = "5")
    @Min(0)
    @Max(5)
    private Integer ratingValue;
}
