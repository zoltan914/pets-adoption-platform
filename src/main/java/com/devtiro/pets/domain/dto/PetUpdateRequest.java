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
public class PetUpdateRequest {
    private String name;
    private String description;
    private Species species;
    private Integer age;
    private PetSize petSize;
    @Valid
    private AddressDto address;
    @Valid
    private GeoPointDto location;

}
