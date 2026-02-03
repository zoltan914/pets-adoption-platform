package com.devtiro.pets.domain.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionApplicationUpdateRequest {

    private String alternatePhone;

    @Valid
    private AddressDto address;

    @Valid
    private LivingSituationDto livingSituation;

    // Additional Information
    private String adoptionReason;

    private String petExperience;

    private String veterinarianName;

    private String veterinarianContact;

    private String references;

    private String additionalComments;

}
