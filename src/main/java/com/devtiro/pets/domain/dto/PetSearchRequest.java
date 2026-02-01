package com.devtiro.pets.domain.dto;

import com.devtiro.pets.domain.entity.PetSize;
import com.devtiro.pets.domain.entity.Species;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetSearchRequest {

    private Species species;
    private Integer minAge;
    private Integer maxAge;
    private PetSize petSize;

    // Geolocation filter
    @Valid
    private GeoPointDto location; // Center point for radius search
    private Double distance; // Distance in kilometers from the location point

}
