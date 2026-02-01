package com.devtiro.pets.domain.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    @NotBlank(message = "street is required")
    private String street;
    @NotBlank(message = "city name is required")
    private String city;
    @NotBlank(message = "state name is required")
    private String state;
    @NotBlank(message = "zipCode name is required")
    private String zipCode;

}
