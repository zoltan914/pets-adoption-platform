package com.devtiro.pets.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing living situation details for an adoption application
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivingSituation {

    private String residenceType; // HOUSE, APARTMENT, CONDO, etc.
    
    private Boolean ownOrRent; // true = own, false = rent
    
    private Boolean landlordPermission; // Required if renting
    
    private String landlordContact; // Contact information if renting
    
    private Boolean hasYard; // Does the residence have a yard
    
    private String yardSize; // Size of yard if applicable (SMALL, MEDIUM, LARGE)
    
    private Boolean isYardFenced; // Is the yard fenced
    
    private Integer numberOfAdults; // Number of adults in household
    
    private Integer numberOfChildren; // Number of children in household
    
    private String childrenAges; // Ages of children if applicable
    
    private Boolean hasOtherPets; // Does household have other pets
    
    private String otherPetsDetails; // Details about other pets if applicable
    
    private String activityLevel; // Activity level of household (LOW, MODERATE, HIGH)
    
    private Integer hoursAlonePerDay; // Average hours pet will be alone per day
}
