package com.devtiro.pets.domain.dto;

import com.devtiro.pets.domain.entity.PetSize;
import com.devtiro.pets.domain.entity.Species;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetCreateRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    @Size(max = 1000, message = "description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "species is required")
    private Species species;
    @NotNull(message = "age is required")
    private Integer age;
    @NotNull(message = "petSize is required")
    private PetSize petSize;

    @NotNull(message = "address is required")
    @Valid
    private AddressDto address;

    @NotNull(message = "location is required")
    @Valid
    private GeoPointDto location;

}
