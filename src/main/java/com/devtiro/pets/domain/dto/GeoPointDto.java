package com.devtiro.pets.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoPointDto {
    @NotNull(message = "latitude is required")
    private Double lat;
    @NotNull(message = "longitude is required")
    private Double lon;
}
