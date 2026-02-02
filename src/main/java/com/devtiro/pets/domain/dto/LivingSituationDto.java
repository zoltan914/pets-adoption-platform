package com.devtiro.pets.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivingSituationDto {

    @NotNull(message = "Residence type is required")
    private String residenceType;
    
    @NotNull(message = "Please specify if you own or rent")
    private Boolean ownOrRent;
    
    private Boolean landlordPermission;
    
    private String landlordContact;
    
    private Boolean hasYard;
    
    private String yardSize;
    
    private Boolean isYardFenced;
    
    @NotNull(message = "Number of adults is required")
    @Min(value = 1, message = "At least one adult must be in the household")
    private Integer numberOfAdults;
    
    @NotNull(message = "Number of children is required")
    @Min(value = 0, message = "Number of children cannot be negative")
    private Integer numberOfChildren;
    
    private String childrenAges;
    
    @NotNull(message = "Please specify if you have other pets")
    private Boolean hasOtherPets;
    
    private String otherPetsDetails;
    
    @NotNull(message = "Activity level is required")
    private String activityLevel;
    
    @NotNull(message = "Hours alone per day is required")
    @Min(value = 0, message = "Hours alone cannot be negative")
    private Integer hoursAlonePerDay;
}
